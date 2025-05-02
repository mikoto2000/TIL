package dev.mikoto2000.springbootstudy.validation.firststep;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.mikoto2000.springbootstudy.validation.firststep.model.User;
import jakarta.validation.Validator;

@SpringBootTest
class FirststepApplicationTests {

  @Autowired
  private Validator validator;

  @Test
  void testValidateion() {

    User user1 = new User("a", "b", true, null);
    var result1 = validator.validate(user1);
    System.out.println(result1);

    User user2 = new User("c", "d", false, null);
    var result2 = validator.validate(user2);
    System.out.println(result2);
  }

}
