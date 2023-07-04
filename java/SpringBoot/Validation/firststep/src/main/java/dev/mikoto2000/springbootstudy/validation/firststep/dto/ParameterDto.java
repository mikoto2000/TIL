package dev.mikoto2000.springbootstudy.validation.firststep.dto;

import org.hibernate.validator.constraints.Email;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    // 必須
    @NotNull
    // 負の数は禁止
    @Min(0)
    private int integer;
    // 必須
    @NotNull
    private String name;
    // 必須
    @NotNull
    @Email
    private String mail;
}
