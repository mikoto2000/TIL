package dev.mikoto2000.study.springboot.data.rest.example.aspect;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponse {
    List<ValidationError> errors;
}