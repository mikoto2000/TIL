package dev.mikoto2000.study.springboot.mybatis.firststep;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.mikoto2000.study.springboot.mybatis.firststep.entity.User;
import dev.mikoto2000.study.springboot.mybatis.firststep.service.UserService;

@SpringBootApplication
public class FirststepApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(FirststepApplication.class, args);
        var userService = ctx.getBean(UserService.class);

        User user = userService.search(1L);
        System.out.println(user);

        List<User> users = userService.searchAll();
        System.out.println(users);
    }

}
