package dev.mikoto2000.study.springboot.websocket.firststep.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dev.mikoto2000.study.springboot.websocket.firststep.MyWebSocketHandler;
import dev.mikoto2000.study.springboot.websocket.firststep.config.WebSocketConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * DynamicHandlerController
 */
@Slf4j
@RestController
public class DynamicHandlerController {

    private WebSocketConfig wsConfig;

    /**
     * Constructor
     */
    public DynamicHandlerController(WebSocketConfig wsConfig) {
        this.wsConfig = wsConfig;
    }


    @GetMapping("/addAllowedListener/{id}")
    public String addHandler(@PathVariable("id") String id) {
        log.info("added handler to '{}'", id);
        wsConfig.addAllowedListener(id);
        return "{result: \"ok\"}";
    }
}

