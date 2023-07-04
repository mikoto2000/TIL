package dev.mikoto2000.springbootstudy.validation.firststep.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    private String mail;
}
