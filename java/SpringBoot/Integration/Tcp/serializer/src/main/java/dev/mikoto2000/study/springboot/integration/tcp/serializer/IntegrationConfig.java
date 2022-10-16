package dev.mikoto2000.study.springboot.integration.tcp.serializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.messaging.MessageHeaders;

import dev.mikoto2000.study.springboot.integration.tcp.serializer.model.Request;
import dev.mikoto2000.study.springboot.integration.tcp.serializer.model.Response;
import dev.mikoto2000.study.springboot.integration.tcp.serializer.serializer.*;

import dev.mikoto2000.study.springboot.integration.tcp.serializer.transformer.BytesToRequestTransformer;
import dev.mikoto2000.study.springboot.integration.tcp.serializer.transformer.BytesToResponseTransformer;
import dev.mikoto2000.study.springboot.integration.tcp.serializer.transformer.RequestToBytesTransformer;
import dev.mikoto2000.study.springboot.integration.tcp.serializer.transformer.ResponseToBytesTransformer;

@EnableIntegration
@Configuration
public class IntegrationConfig {

    private final RequestSerializer requestSerializer = new RequestSerializer();

    private final ResponseSerializer responseSerializer = new ResponseSerializer();

    private final TcpServerProps props;

    private final RequestToBytesTransformer requestToBytesTransformer;

    private final BytesToResponseTransformer bytesToResponseTransformer;

    private final BytesToRequestTransformer bytesToRequestTransformer;

    private final ResponseToBytesTransformer responseToBytesTransformer;


    public IntegrationConfig(TcpServerProps props,
            RequestToBytesTransformer requestToBytesTransformer,
            BytesToResponseTransformer bytesToResponseTransformer,
            BytesToRequestTransformer bytesToRequestTransformer,
            ResponseToBytesTransformer responseToBytesTransformer) {
        this.requestToBytesTransformer = requestToBytesTransformer;
        this.bytesToResponseTransformer = bytesToResponseTransformer;
        this.bytesToRequestTransformer = bytesToRequestTransformer;
        this.responseToBytesTransformer = responseToBytesTransformer;
        this.props = props;
    }

    @Bean
    public IntegrationFlow sendFlow() {
        return f-> f
                .transform(requestToBytesTransformer)
                .handle(
                    Tcp.outboundGateway(
                        Tcp.nioClient(this.props.getHost(), this.props.getPort())
                            .serializer(requestSerializer)
                            .deserializer(responseSerializer)
                    )
                )
                .transform(bytesToResponseTransformer);
    }

    @Bean
    public IntegrationFlow serverFlow() {
        return IntegrationFlows
                .from(
                    Tcp.inboundGateway(
                        Tcp.nioServer(this.props.getPort())
                            .serializer(responseSerializer)
                            .deserializer(requestSerializer)
                    )
                )
                .transform(bytesToRequestTransformer)
                .handle(new GenericHandler<Request>() {
                    @Override
                    public Object handle(Request p, MessageHeaders h) {
                        System.out.println("serverFlow header: " + h);
                        System.out.println("serverFlow payload: " + p);

                        var response = new Response((short)p.getType(), p.getPayload());

                        return response;
                    }
                })
                /* 上記 handle メソッドと同じ意味
                .<Request>handle((p, h) -> {
                    System.out.println("serverFlow header: " + h);
                    System.out.println("serverFlow payload: " + p);

                    var response = new Response((short)p.getType(), p.getPayload());

                    return response;
                })
                */
                .transform(responseToBytesTransformer)
                .get();
    }
}
