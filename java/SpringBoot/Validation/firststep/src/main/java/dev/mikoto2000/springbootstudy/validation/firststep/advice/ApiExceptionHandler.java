package dev.mikoto2000.springbootstudy.validation.firststep.advice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.mikoto2000.springbootstudy.validation.firststep.advice.dto.error.ErrorDto;
import dev.mikoto2000.springbootstudy.validation.firststep.advice.dto.error.ValidationErrorDto;
import lombok.extern.slf4j.Slf4j;

/**
 * ExceptionHandler
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ValidationErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

    List<ErrorDto> errors = e.getFieldErrors().stream().map((error) ->
        new ErrorDto(error.getField(), error.getDefaultMessage())
    ).collect(Collectors.toList());

    return new ValidationErrorDto(errors);
  }
}

