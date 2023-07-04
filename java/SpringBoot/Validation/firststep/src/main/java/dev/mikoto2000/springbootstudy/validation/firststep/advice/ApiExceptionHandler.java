package dev.mikoto2000.springbootstudy.validation.firststep.advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.mikoto2000.springbootstudy.validation.firststep.advice.model.error.ApiError;
import dev.mikoto2000.springbootstudy.validation.firststep.advice.model.error.ValidationError;
import lombok.extern.slf4j.Slf4j;

/**
 * ExceptionHandler
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ValidationError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

    List<ApiError> errors = e.getFieldErrors().stream().map((error) ->
        new ApiError(error.getField(), error.getDefaultMessage())
    ).collect(Collectors.toList());

    return new ValidationError(errors);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ValidationError handleMethodArgumentNotValidException(HttpMessageNotReadableException e) {

    List<ApiError> errors = Arrays.asList(new ApiError(null, e.getLocalizedMessage()));

    return new ValidationError(errors);
  }
}

