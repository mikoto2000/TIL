package dev.mikoto2000.springboot.security.apikey.firststep.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestParam String message) {
    System.out.println(userDetails);
    return message;
  }

  @GetMapping("/api/echo")
  public String apiEcho(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestParam String message) {
    System.out.println(userDetails);
    return message;
  }
}
