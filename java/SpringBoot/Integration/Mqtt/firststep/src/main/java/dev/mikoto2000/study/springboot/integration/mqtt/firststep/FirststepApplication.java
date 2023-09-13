package dev.mikoto2000.study.springboot.integration.mqtt.firststep;

import java.util.concurrent.CountDownLatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import dev.mikoto2000.study.springboot.integration.mqtt.firststep.model.User;
import dev.mikoto2000.study.springboot.integration.mqtt.firststep.service.MqttSendService;

/**
 * アプリケーションクラス。
 */
@SpringBootApplication
public class FirststepApplication {

    private static final CountDownLatch latch = new CountDownLatch(1);

    /**
     * メインメソッド。
     *
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx =
                SpringApplication.run(FirststepApplication.class, args);

        try {
            MqttSendService mss = ctx.getBean(MqttSendService.class);
            Thread.sleep(3000);
            mss.sendToMqtt("testtopic/stringpayloadmessage", "👺stringpayloadmessage");
            mss.sendToMqtt("testtopic/bytearraypayloadmessage", "👪bytearraypayloadmessage");

            mss.sendToMqtt("testtopic/stringpayloadmessage", new User("firstName", "lastName"));
            mss.sendToMqtt("testtopic/bytearraypayloadmessage",  new User("firstName", "lastName"));

            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
