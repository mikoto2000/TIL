package dev.mikoto2000.study.springboot.integration.tcp.serializer;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import dev.mikoto2000.study.springboot.integration.tcp.serializer.model.Request;
import dev.mikoto2000.study.springboot.integration.tcp.serializer.model.Response;

@MessagingGateway(name = "send", defaultRequestChannel = "sendFlow.input")
public interface OutboundGateway {
    @Gateway
    Response send(Request request);
}

