package dev.mikoto2000.springbootstudy.retry.firststep;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class RetryableService {

    @Retryable(value = {RuntimeException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void hello(String message) {
        System.out.println("Hello, " + message + "!");

        throw new RuntimeException("error");
    }

}
