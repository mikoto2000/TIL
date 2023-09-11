package dev.mikoto2000.study.springboot.integration.mqtt.firststep.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;

import dev.mikoto2000.study.springboot.integration.mqtt.firststep.property.MqttProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * MqttConfiguration
 */
@Configuration
@EnableIntegration
@Slf4j
public class MqttConfiguration {

    private final MqttProperties mqttProperties;

    @Autowired
    public MqttConfiguration(MqttProperties mqttProperties) {
        this.mqttProperties = mqttProperties;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {

        log.atDebug()
                .setMessage("mqttProperties: {}")
                .addArgument(() -> mqttProperties)
                .log();

        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] { mqttProperties.getUrl() });
        options.setUserName(mqttProperties.getUserName());
        options.setPassword(mqttProperties.getPassword().toCharArray());
        options.setAutomaticReconnect(true);
        options.setMaxReconnectDelay(30000);

        log.atDebug()
                .setMessage("MqttConnectOptions: {}")
                .addArgument(() -> options.toString())
                .log();

        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public IntegrationFlow mqttStringPayloadInFlow() {
        // トピックを受信するアダプタを生成
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                "mqttStringPayloadClient", mqttClientFactory(), "testtopic/stringpayloadmessage");

        return IntegrationFlow
                .from(adapter)
                .handle(message -> {
                    System.out.println("Received MQTT topic: " + message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC));
                    System.out.println("Received MQTT message: " + message.getPayload());
                })
                .get();
    }

    @Bean
    public IntegrationFlow mqttByteArrayPayloadInFlow() {
        // トピックを受信するアダプタを生成
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                "mqttByteArrayPayloadClient", mqttClientFactory(), "testtopic/bytearraypayloadmessage");

        // ペイロードを byte[] で取得できるように設定を変更する
        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
        converter.setPayloadAsBytes(true);
        adapter.setConverter(converter);

        return IntegrationFlow
                .from(adapter)
                .handle(message -> {
                    byte[] payload = (byte[]) message.getPayload();
                    System.out.println("Received MQTT topic: " + message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC));
                    System.out.println("Received MQTT message raw: " + message.getPayload());
                    System.out.println("Received MQTT message string: " + new String(payload));
                })
                .get();
    }
}