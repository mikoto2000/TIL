package dev.mikoto2000.springbootstudy.validation.firststep.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.mikoto2000.springbootstudy.validation.firststep.dto.ParameterDto;
import dev.mikoto2000.springbootstudy.validation.firststep.dto.UserDto;
import dev.mikoto2000.springbootstudy.validation.firststep.model.User;
import lombok.extern.slf4j.Slf4j;

/**
 * ValidationTestController
 */
@Slf4j
@RestController
public class ValidationTestController {
    @PostMapping("validation-test")
    public UserDto validationTest(@RequestBody ParameterDto parameter) {

        // 普通ならサービスを呼んでモデルを取得して、 Dto に詰め替える
        User user = new User(parameter.getName() + parameter.getInteger(), parameter.getMail());

        return new UserDto(user.getName(), user.getMail());
    }
}

