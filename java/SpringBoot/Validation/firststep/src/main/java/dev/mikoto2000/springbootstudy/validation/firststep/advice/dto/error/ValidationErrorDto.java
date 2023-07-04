package dev.mikoto2000.springbootstudy.validation.firststep.advice.dto.error;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ValidationErrorDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorDto {
    private List<ErrorDto> errors;
}

