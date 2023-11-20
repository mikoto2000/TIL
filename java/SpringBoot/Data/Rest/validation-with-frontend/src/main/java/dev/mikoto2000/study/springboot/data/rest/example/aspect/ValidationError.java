package dev.mikoto2000.study.springboot.data.rest.example.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ValidationError {
    private String targetName;
    private String message;
}