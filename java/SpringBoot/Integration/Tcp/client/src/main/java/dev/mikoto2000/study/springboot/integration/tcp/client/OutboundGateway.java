package dev.mikoto2000.study.springboot.integration.tcp.client;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(name = "send", defaultRequestChannel = "integrationFlow.input")
public interface OutboundGateway {
    @Gateway
    String send(String message);
}

