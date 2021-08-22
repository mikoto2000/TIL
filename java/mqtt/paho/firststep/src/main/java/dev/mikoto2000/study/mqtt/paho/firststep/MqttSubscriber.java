package dev.mikoto2000.study.mqtt.paho.firststep;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * MqttSubscriber
 */
public class MqttSubscriber implements MqttCallback {

    private final ExecutorService pool;

    /**
     * Constructor
     */
    public MqttSubscriber() {
        this.pool = Executors.newFixedThreadPool(10);
    }

    @Override
    public void connectionLost(Throwable arg0) {
        // do nothing
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
        // do nothing
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        pool.submit(() -> {
            System.out.println(String.format("topic: %s, message: %s", topic, new String(message.getPayload(), java.nio.charset.StandardCharsets.UTF_8)));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
