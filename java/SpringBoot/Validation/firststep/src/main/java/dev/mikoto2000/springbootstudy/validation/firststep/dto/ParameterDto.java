package dev.mikoto2000.springbootstudy.validation.firststep.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ParameterDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParameterDto {
    private int integer;
    private String name;
    private String mail;
}
