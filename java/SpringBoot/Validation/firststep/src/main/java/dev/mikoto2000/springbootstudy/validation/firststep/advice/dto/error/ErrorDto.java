package dev.mikoto2000.springbootstudy.validation.firststep.advice.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ErrorDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    private String fieldName;
    private String message;
}
