package dev.mikoto2000.study.springboot.integration.tcp.serializer;

import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

@Component
public class ClientMessageTransformer implements GenericTransformer<String, String> {

    @Override
    public String transform(String input) {
        return input.toUpperCase();
    }
}

