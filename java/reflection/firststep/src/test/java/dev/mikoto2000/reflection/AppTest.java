package dev.mikoto2000.reflection;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

import dev.mikoto2000.reflection.model.User;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testReflection_newInstance() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
      final Class<User> userClass = User.class;

      User user1 = userClass.getDeclaredConstructor().newInstance();
      user1.setName("uikoto2000");
      user1.setAge(2000);
      user1.setEmail("mikoto2000@gmail.com");

      System.out.println(user1);
    }
}
