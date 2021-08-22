package dev.mikoto2000.study.vertx.mqtt;

import java.time.LocalDateTime;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    MqttClient client = MqttClient.create(vertx);

    client.connect(1883, "localhost", s -> {
        System.out.println("connected localhost:1883.");

    client.publishHandler(topic -> {
        System.out.println(String.format("{ topic: %s, payload: %s }", topic.topicName(), topic.payload().toString()));
    }).subscribe("temperature", 2);

    vertx.setPeriodic(1000, id -> {
      client.publish("temperature",
        Buffer.buffer(LocalDateTime.now().toString()),
        MqttQoS.AT_LEAST_ONCE,
        false,
        false);
      });
    });
  }
}
