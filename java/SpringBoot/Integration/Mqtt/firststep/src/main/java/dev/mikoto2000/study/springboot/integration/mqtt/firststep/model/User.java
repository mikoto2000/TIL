package dev.mikoto2000.study.springboot.integration.mqtt.firststep.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ユーザーを表すクラス。
 */
@AllArgsConstructor
@Data
public class User {
    private String firstName;
    private String lastName;
}
