package dev.mikoto2000.study.springboot.integration.mqtt.firststep.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

/**
 * MQTT 送信サービス。
 */
@AllArgsConstructor
@Service
public class MqttSendService {

    /**
     * MQTT 送信用チャンネル。
     */
    private MessageChannel mqttOutboundChannel;

    /**
     * MQTT トピック送信。メソッド。
     *
     * @param topic トピック名
     * @param data 送信データ
     */
    public void sendToMqtt(String topic, String data) {
        mqttOutboundChannel
                .send(MessageBuilder
                        .withPayload(data)
                        .setHeader(MqttHeaders.TOPIC, topic)
                        .build());
    }
}
