package dev.mikoto2000.study.springboot.mybatis.firststep;

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
    }

}
