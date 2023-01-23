package dev.mikoto2000.study.springboot.oidc.firststep.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mikoto2000.study.springboot.oidc.firststep.model.User;

/**
 * FirststepController
 */
@RestController
public class FirststepController {
    @GetMapping("/users")
    public List<User> getUsers() {
        var users = Arrays.asList(new User[]{new User("test"), new User("most")});
        return users;
    }
    @CrossOrigin
    @GetMapping("/test")
    public List<User> getUsersTest() {
        var users = Arrays.asList(new User[]{new User("aaa"), new User("bbb")});
        return users;
    }
}
