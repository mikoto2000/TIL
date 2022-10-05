package dev.mikoto2000.springbootstudy.retry.firststep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class FirststepApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(FirststepApplication.class, args);
        var retryableService = ctx.getBean(RetryableService.class);
        retryableService.hello("World");
    }

}

