package dev.mikoto2000.springbootstudy.retry.firststep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableRetry
@EnableScheduling
public class FirststepApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirststepApplication.class, args);
    }

}

