package dev.mikoto2000.study.springboot.integration.tcp.serializer;

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

    private final ServerMessageTransformer serverMessageTransformer;

    private final ClientMessageTransformer clientMessageTransformer;

    public IntegrationConfig(TcpServerProps props, ServerMessageTransformer serverMessageTransformer, ClientMessageTransformer clientMessageTransformer) {
        this.props = props;
        this.serverMessageTransformer = serverMessageTransformer;
        this.clientMessageTransformer = clientMessageTransformer;
    }

    @Bean
    public IntegrationFlow sendFlow() {
        return f-> f.handle(Tcp.outboundGateway(
                Tcp.nioClient(this.props.getHost(), this.props.getPort())
                    .serializer(TcpCodecs.crlf())
                    .deserializer(TcpCodecs.singleTerminator((byte)'!'))))
            .transform(Transformers.objectToString());
    }

    @Bean
    public IntegrationFlow serverFlow() {
        return IntegrationFlows.from(Tcp.inboundGateway(Tcp.nioServer(this.props.getPort())
            .serializer(TcpCodecs.crlf())
            .deserializer(TcpCodecs.crlf())
            .get()))
            .transform(Transformers.objectToString())
            .transform(this.serverMessageTransformer)
            .get();
    }
}

