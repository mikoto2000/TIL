package dev.mikoto2000.springbootstudy.validation.firststep.service;

import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * ValidationService
 */
@Service
@RequiredArgsConstructor
public class ValidationService {

  private final Validator validator;

  public <T> List<ValidationError> validate(T bean) {
      Set<ConstraintViolation<T>> violations = validator.validate(bean);

      return violations.stream()
              .map(violation -> new ValidationError(
                violation.getPropertyPath().toString(),
                violation.getMessage()
              ))
              .toList();
  }

  @Data
  @RequiredArgsConstructor
  public static class ValidationError {
    private final String field;
    private final String message;
  }
}
