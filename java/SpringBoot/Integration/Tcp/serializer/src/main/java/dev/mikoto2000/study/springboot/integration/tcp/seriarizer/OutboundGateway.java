package dev.mikoto2000.study.springboot.integration.tcp.serializer;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(name = "send", defaultRequestChannel = "sendFlow.input")
public interface OutboundGateway {
    @Gateway
    String send(String message);
}

