package dev.mikoto2000.study.springboot.integration.mqtt.firststep.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;

import dev.mikoto2000.study.springboot.integration.mqtt.firststep.property.MqttProperties;

/**
 * MqttConfiguration。
 */
@AllArgsConstructor
@Configuration
@EnableIntegration
@Slf4j
public class MqttConfiguration {

    /**
     * MQTT のプロパティ。
     */
    private final MqttProperties mqttProperties;

    /**
     * MQTT 送受信用クライアントファクトリ生成メソッド。
     *
     * @return MQTT 送受信用クライアントファクトリ
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {

        log.atDebug()
                .setMessage("mqttProperties: {}")
                .addArgument(() -> mqttProperties)
                .log();

        // MQTT 接続オプションの作成
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] {mqttProperties.getUrl()});
        options.setUserName(mqttProperties.getUserName());
        options.setPassword(mqttProperties.getPassword().toCharArray());
        options.setAutomaticReconnect(true);
        options.setMaxReconnectDelay(30000);

        log.atDebug()
                .setMessage("MqttConnectOptions: {}")
                .addArgument(() -> options.toString())
                .log();

        // 作成した MQTT 接続オプションを使いファクトリを生成
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(options);
        return factory;
    }

    /**
     * MQTT 受信処理用 Executor 作成メソッド。
     * スレッドプールを用意し、受信処理ごとに空いているスレッドを使用して
     *
     * @return MQTT 受信処理用 Executor
     */
    @Bean
    public Executor mqttInboundExecutor() {
        return Executors.newCachedThreadPool();
    }

    /**
     * MQTT String ペイロード受信用チャンネル作成メソッド。
     *
     * @return MQTT String ペイロード受信用チャンネル
     */
    @Bean
    public MessageChannel mqttStringPayloadInboundChannel() {
        return MessageChannels.executor(mqttInboundExecutor()).getObject();
    }

    /**
     * MQTT String ペイロード受信用の IntegrationFlow 作成メソッド。
     *
     * @return MQTT String ペイロード受信用の IntegrationFlow
     */
    @Bean
    public IntegrationFlow mqttStringPayloadInFlow() {
        // トピックを受信するアダプタを生成
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        "mqttStringPayloadClient", mqttClientFactory(),
                        "testtopic/stringpayloadmessage");

        return IntegrationFlow
                .from(adapter)
                .channel(mqttStringPayloadInboundChannel())
                .handle(message -> {
                    log.info("Received MQTT topic: "
                            + message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC));
                    log.info("Received MQTT message: " + message.getPayload());
                })
                .get();
    }

    /**
     * MQTT Byte Array ペイロード受信用チャンネル作成メソッド。
     *
     * @return MQTT Byte Array ペイロード受信用チャンネル
     */
    @Bean
    public MessageChannel mqttByteArrayPayloadInboundChannel() {
        return MessageChannels.executor(mqttInboundExecutor()).getObject();
    }

    /**
     * MQTT Byte Array ペイロード受信用 IntegrationFlow 作成メソッド。
     *
     * @return MQTT Byte Array ペイロード受信用 IntegrationFlow
     */
    @Bean
    public IntegrationFlow mqttByteArrayPayloadInFlow() {
        // トピックを受信するアダプタを生成
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        "mqttByteArrayPayloadClient",
                        mqttClientFactory(),
                        "testtopic/bytearraypayloadmessage");

        // ペイロードを byte[] で取得できるように設定を変更する
        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
        converter.setPayloadAsBytes(true);
        adapter.setConverter(converter);

        return IntegrationFlow
                .from(adapter)
                .channel(mqttByteArrayPayloadInboundChannel())
                .handle(message -> {
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    log.info("Received MQTT topic: "
                            + message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC));
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    log.info("Received MQTT message raw: " + message.getPayload());
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    byte[] payload = (byte[]) message.getPayload();
                    log.info("Received MQTT message string: " + new String(payload));
                })
                .get();
    }

    /**
     * MQTT 送信用メッセージハンドラ作成メソッド。
     *
     * @return MQTT メッセージ送信用ハンドラ
     */
    @Bean
    public MqttPahoMessageHandler messageHandler() {
        MqttPahoMessageHandler handler =
                new MqttPahoMessageHandler("sendClient", mqttClientFactory());
        handler.setAsync(true);
        handler.setDefaultTopic("testtopic/stringpayloadmessage");
        return handler;
    }

    /**
     * MQTT 送信用チャンネル作成メソッド。
     *
     * @return MQTT 送信用チャンネル
     */
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return MessageChannels.direct().getObject();
    }

    /**
     * MQTT 送信用 IntegrationFlow 作成メソッド。
     *
     * @return MQTT 送信用 IntegrationFlow
     */
    @Bean
    public IntegrationFlow mqttOutboundFlow() {
        return IntegrationFlow
                .from(mqttOutboundChannel())
                .handle(messageHandler())
                .get();
    }
}
