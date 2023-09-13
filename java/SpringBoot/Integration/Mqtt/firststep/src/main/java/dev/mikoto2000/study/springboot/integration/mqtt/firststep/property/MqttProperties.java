package dev.mikoto2000.study.springboot.integration.mqtt.firststep.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MQTT 用プロパティクラス。
 */
@Component
@ConfigurationProperties("mqtt")
@Data
public class MqttProperties {
    private String url;
    private String userName;
    private String password;
}
