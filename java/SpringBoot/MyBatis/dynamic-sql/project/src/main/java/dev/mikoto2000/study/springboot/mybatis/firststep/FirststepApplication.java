package dev.mikoto2000.study.springboot.mybatis.firststep;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.mikoto2000.study.springboot.mybatis.firststep.domain.entity.User;
import dev.mikoto2000.study.springboot.mybatis.firststep.domain.service.UserService;

@SpringBootApplication
public class FirststepApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(FirststepApplication.class, args);
        var userService = ctx.getBean(UserService.class);

        User user = userService.search(1L);
        System.out.println(user);

        List<User> users = userService.searchAll();
        System.out.println(users);

        int insertCount = userService.insert(1000, "inserted user");
        System.out.println(insertCount);

        List<User> users2 = userService.searchAll();
        System.out.println(users2);

        userService.delete(1000);

        List<User> users3 = userService.searchAll();
        System.out.println(users3);

        List<User> users4 = Arrays.<User>asList(
          new User(1000L, "test1"), new User(1001L, "test2"), new User(1002L, "test3")
        );
        userService.insertBulk(users4);

        List<User> users5 = userService.searchAll();
        System.out.println(users5);

        userService.delete(1000);
        userService.delete(1001);
        userService.delete(1002);

        List<User> users6 = userService.searchAll();
        System.out.println(users6);
    }

}
