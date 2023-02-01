package dev.mikoto2000.study.springboot.websocket.firststep.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import dev.mikoto2000.study.springboot.websocket.firststep.MyWebSocketHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * WebSocketConfig
 */
@Slf4j
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private WebSocketHandlerRegistry registry;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        this.registry = registry;
        registry.addHandler(messageHandler(), "/default");
        registry.addHandler(messageHandler(), "/dafoult");
    }

    public WebSocketHandler messageHandler() {
        return new MyWebSocketHandler();
    }

}

