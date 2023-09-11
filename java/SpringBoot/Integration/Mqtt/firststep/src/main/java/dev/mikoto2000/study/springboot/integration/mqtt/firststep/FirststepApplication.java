package dev.mikoto2000.study.springboot.integration.mqtt.firststep;

import java.util.concurrent.CountDownLatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FirststepApplication {

    private static final CountDownLatch latch = new CountDownLatch(1);

	public static void main(String[] args) {
		SpringApplication.run(FirststepApplication.class, args);

		try {
			latch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
