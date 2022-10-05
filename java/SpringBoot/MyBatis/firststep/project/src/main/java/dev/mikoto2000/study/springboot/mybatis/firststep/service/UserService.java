package dev.mikoto2000.study.springboot.mybatis.firststep.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.mikoto2000.study.springboot.mybatis.firststep.dto.UserSearchRequest;
import dev.mikoto2000.study.springboot.mybatis.firststep.entity.User;
import dev.mikoto2000.study.springboot.mybatis.firststep.repository.UserMapper;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> searchAll() {
        return userMapper.searchAll();
    }

    public User search(Long id) {
        UserSearchRequest userSearchRequest = new UserSearchRequest(id);
        return userMapper.search(userSearchRequest);
    }
}
