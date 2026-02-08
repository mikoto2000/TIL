package dev.mikoto2000.study.springboot.logging.firststep.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.mikoto2000.study.springboot.logging.firststep.service.GreetingService;
import lombok.RequiredArgsConstructor;

/**
 * HelloController
 */
@RequiredArgsConstructor
@RestController
public class HelloController {

  private final GreetingService gs;

  @GetMapping("hello")
  public String hello(
      @RequestParam String message) {
    return gs.greet(message);
  }
}

