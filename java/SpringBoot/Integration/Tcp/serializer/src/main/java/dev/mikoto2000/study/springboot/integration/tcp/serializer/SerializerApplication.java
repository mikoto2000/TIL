package dev.mikoto2000.study.springboot.integration.tcp.serializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SerializerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SerializerApplication.class, args);
	}

}
