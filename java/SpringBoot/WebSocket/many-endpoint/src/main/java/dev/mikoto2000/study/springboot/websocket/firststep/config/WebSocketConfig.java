package dev.mikoto2000.study.springboot.websocket.firststep.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import dev.mikoto2000.study.springboot.websocket.firststep.MyWebSocketHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * WebSocketConfig
 */
@Slf4j
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private Set<String> ids = new HashSet<String>();

    private WebSocketHandlerRegistry registry;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        this.registry = registry;
        registry.addHandler(messageHandler(), "/ws/*")
            .addInterceptors(auctionInterceptor());
    }

    public WebSocketHandler messageHandler() {
        return new MyWebSocketHandler();
    }

    public HandshakeInterceptor auctionInterceptor() {
        return new HandshakeInterceptor() {
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                    WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

                final Path path = Paths.get(request.getURI().getPath());
                log.info("path {}", path);
                final String id = path.getName(path.getNameCount() - 1).toString();
                log.info("id {}", id);

                if (ids.contains(id)) {
                    return true;
                } else {
                    response.setStatusCode(HttpStatus.NOT_FOUND);
                    return false;
                }
            }

            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, 
                    WebSocketHandler wsHandler, Exception exception) {
                // Nothing to do after handshake
            }
        };
    }

    public void addAllowedListener(String path) {
        this.ids.add(path);
    }

}

