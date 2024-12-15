package dev.mikoto2000.study.springboot.web.practice20241215.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * EchoController
 */
@RestController
public class EchoController {

  @GetMapping(path = "/hello")
  public String hello() {
    return "Hello, World!";
  }
}
