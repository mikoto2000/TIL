package dev.mikoto2000.study.springboot.integration.mqtt.firststep.service;

import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 * MQTT 送信サービス
 */
@AllArgsConstructor
@Service
public class MqttSendService {

    /**
     * MQTT 送信用チャンネル
     */
    private MessageChannel mqttOutboundChannel;

    public void sendToMqtt(String topic, String data) {
        mqttOutboundChannel.send(MessageBuilder.withPayload(data)
            .setHeader(MqttHeaders.TOPIC, topic)
            .build());
    }
}
