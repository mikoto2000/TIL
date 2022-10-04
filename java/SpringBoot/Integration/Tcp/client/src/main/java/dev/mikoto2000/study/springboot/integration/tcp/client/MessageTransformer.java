package dev.mikoto2000.study.springboot.integration.tcp.client;

import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

@Component
public class MessageTransformer implements GenericTransformer<String, String> {

    @Override
    public String transform(String input) {
        return input;
    }
}

