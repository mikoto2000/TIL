package dev.mikoto2000.study.springboot.integration.mqtt.firststep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

/**
 * MQTT 送信サービス
 */
@Service
public class MqttSendService {

    /**
     * MQTT 送信用チャンネル
     */
    private MessageChannel mqttOutboundChannel;

    /**
     * コンストラクタ
     * @param mqttOutboundChannel MQTT 送信用チャンネル
     */
    @Autowired
    public MqttSendService(MessageChannel mqttOutboundChannel){
        this.mqttOutboundChannel = mqttOutboundChannel;
    }

    public void sendToMqtt(String topic, String data) {
        mqttOutboundChannel.send(MessageBuilder.withPayload(data)
            .setHeader(MqttHeaders.TOPIC, topic)
            .build());
    }
}
