package dev.mikoto2000.springbootstudy.validation.firststep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String name;
    private String mail;
}
