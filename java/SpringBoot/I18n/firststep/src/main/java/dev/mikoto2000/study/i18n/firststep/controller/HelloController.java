package dev.mikoto2000.study.i18n.firststep.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 */
@RestController
public class HelloController {
  @GetMapping("/hello")
  public String hello() {
    return "Hello, World!";
  }
}
