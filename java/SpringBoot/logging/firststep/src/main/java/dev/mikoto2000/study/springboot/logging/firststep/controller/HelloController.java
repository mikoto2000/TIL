package dev.mikoto2000.study.springboot.logging.firststep.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 */
@RestController
public class HelloController {
  @GetMapping("hello")
  public String hello(
      @RequestParam String message) {
    return message;
  }
}

