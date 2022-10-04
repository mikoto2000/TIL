package dev.mikoto2000.study.springboot.integration.tcp.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.tcp.serializer.TcpCodecs;

@EnableIntegration
@Configuration
public class IntegrationConfig {

    private final TcpServerProps props;

    private final MessageTransformer messageTransformer;

    public IntegrationConfig(TcpServerProps props, MessageTransformer messageTransformer) {
        this.props = props;
        this.messageTransformer = messageTransformer;
    }

    @Bean
    public IntegrationFlow integrationFlow() {
        return f-> f.handle(Tcp.outboundGateway(Tcp.nioClient(this.props.getHost(), this.props.getPort())
            .serializer(TcpCodecs.crlf()))).transform(Transformers.objectToString());
    }
}

