package dev.mikoto2000.springboot.security.apikey.firststep.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * EchoController
 */
@RestController
public class EchoController {

  @GetMapping("/echo")
  public String echo(
      @RequestParam String message) {
    return message;
  }

  @GetMapping("/api/echo")
  public String apiEcho(
      @RequestParam String message) {
    return message;
  }
}
