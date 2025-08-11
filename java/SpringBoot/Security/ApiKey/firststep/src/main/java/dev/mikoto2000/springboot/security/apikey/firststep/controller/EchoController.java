package dev.mikoto2000.springboot.security.apikey.firststep.controller;

import org.springframework.stereotype.Controller;

/**
 * EchoController
 */
@Controller
public class EchoController {
  public String echo(
      String message) {
    return message;
  }
}
