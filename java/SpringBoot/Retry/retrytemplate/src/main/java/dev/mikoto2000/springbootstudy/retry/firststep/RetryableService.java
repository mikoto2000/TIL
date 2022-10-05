package dev.mikoto2000.springbootstudy.retry.firststep;

import org.springframework.stereotype.Service;

@Service
public class RetryableService {

    public void hello(String message) {
        System.out.println("Hello, " + message + "!");

        throw new RuntimeException("error");
    }

}
