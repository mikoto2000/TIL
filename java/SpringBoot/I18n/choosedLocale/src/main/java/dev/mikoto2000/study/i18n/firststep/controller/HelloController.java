package dev.mikoto2000.study.i18n.firststep.controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * HelloController
 */
@RestController
@RequiredArgsConstructor
public class HelloController {

  private final MessageSource messageSource;

  @GetMapping("/hello")
  public String hello(Locale locale) {

    var prefix = messageSource.getMessage("hello", null, locale);
    var world = messageSource.getMessage("world", null, locale);

    return prefix + ", " + world + "!";
  }
}
