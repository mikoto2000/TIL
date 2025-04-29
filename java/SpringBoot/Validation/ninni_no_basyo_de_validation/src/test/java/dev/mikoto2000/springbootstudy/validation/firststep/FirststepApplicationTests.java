package dev.mikoto2000.springbootstudy.validation.firststep;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;

import dev.mikoto2000.springbootstudy.validation.firststep.model.User;
import dev.mikoto2000.springbootstudy.validation.firststep.service.ValidationService;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
class FirststepApplicationTests {

  @Autowired
  private ValidationService validationService;

  @Test
  void testUser() {
    User user1 = new User("1", "mikoto2000", "mikoto2000@gmail.com");
    var result1 = validationService.validate(user1);
    System.out.println(result1);

    User user2 = new User(null, null, "testing");
    var result2 = validationService.validate(user2);
    System.out.println(result2);
  }
}
