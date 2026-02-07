package dev.mikoto2000.study.springboot.logging.firststep.service;

import org.springframework.stereotype.Service;

/**
 * GreetingService
 */
@Service
public class GreetingService {
  public String greet(String name) {
    return "Hello, " + name;
  }
}
