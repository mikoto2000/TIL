package dev.mikoto2000.springbootstudy.retry.firststep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;

@SpringBootApplication
@EnableRetry
public class FirststepApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(FirststepApplication.class, args);
        var retryableService = ctx.getBean(RetryableService.class);

        RetryTemplate retryTemplate = RetryTemplate.builder()
            .maxAttempts(3)
            .fixedBackoff(1000)
            .retryOn(RuntimeException.class)
            .build();

        retryTemplate.execute((RetryCallback<Void, RuntimeException>)retryContext -> {
            retryableService.hello("World");

            return null;
        });
    }

}

