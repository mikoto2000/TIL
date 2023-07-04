package dev.mikoto2000.springbootstudy.validation.firststep.advice.model.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ErrorDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private String fieldName;
    private String message;
}
