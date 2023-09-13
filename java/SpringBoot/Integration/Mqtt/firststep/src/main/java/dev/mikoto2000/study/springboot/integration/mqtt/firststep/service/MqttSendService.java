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
     * JSON 変換用 ObjectMapper。
     */
    private ObjectMapper objectMapper;

    /**
     * MQTT トピック送信メソッド。
     *
     * @param topic トピック名
     * @param payload 送信データ
     */
    public void sendToMqtt(String topic, String payload) {
        mqttOutboundChannel
                .send(MessageBuilder
                        .withPayload(payload)
                        .setHeader(MqttHeaders.TOPIC, topic)
                        .build());
    }

    /**
     * MQTT トピック送信メソッド。
     *
     * @param topic トピック名
     * @param payload 送信データ
     * @throws JsonProcessingException JSON 文字列への変換に失敗した場合
     */
    public void sendToMqtt(String topic, Object payload) throws JsonProcessingException {

        String stringifyPayload = this.objectMapper.writeValueAsString(payload);

        mqttOutboundChannel
                .send(MessageBuilder
                        .withPayload(stringifyPayload)
                        .setHeader(MqttHeaders.TOPIC, topic)
                        .build());
    }
}
