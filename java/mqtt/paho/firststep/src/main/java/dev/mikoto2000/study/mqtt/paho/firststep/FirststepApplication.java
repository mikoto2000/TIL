package dev.mikoto2000.study.mqtt.paho.firststep;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FirststepApplication {

    public static void main(String[] args) {
        try (ConfigurableApplicationContext ctx = SpringApplication.run(FirststepApplication.class, args)) {
            FirststepApplication app = ctx.getBean(FirststepApplication.class);
            app.run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(String[] args) throws InterruptedException {
        String topic        = "MQTT Examples";
        String content      = "Message from MqttPublishSample";
        int qos             = 2;
        String broker       = "tcp://mqtt.eclipseprojects.io:1883";
        String clientId     = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);

            sampleClient.setCallback(new MqttSubscriber());

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");

            sampleClient.subscribe(topic, qos);

            ExecutorService pool = Executors.newFixedThreadPool(1);

            for (int i = 0; i < 10; i++) {
                pool.submit(() -> {
                    try {
                        System.out.println("Publishing message: "+content);
                        MqttMessage message = new MqttMessage(content.getBytes());
                        message.setQos(qos);
                        sampleClient.publish(topic, message);
                        System.out.println("Message published");
                    } catch (MqttException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });
                Thread.sleep(2000);
            }

            sampleClient.disconnect();
            System.out.println("Disconnected");

            System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
}
