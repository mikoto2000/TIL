package dev.mikoto2000.study.springboot.logging.firststep.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * IndexController
 */
@RestController
public class IndexController {
  @GetMapping("/")
  public String index() {
    return "This is index url";
  }
}
