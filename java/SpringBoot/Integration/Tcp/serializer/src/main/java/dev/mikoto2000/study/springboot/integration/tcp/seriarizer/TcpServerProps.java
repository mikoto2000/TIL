package dev.mikoto2000.study.springboot.integration.tcp.serializer;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("tcp.server")
@Getter
@Setter
public class TcpServerProps {

    private String host;

    private int port;

}

