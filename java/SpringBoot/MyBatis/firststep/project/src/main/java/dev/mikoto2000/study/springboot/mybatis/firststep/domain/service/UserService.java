package dev.mikoto2000.study.springboot.mybatis.firststep.domain.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import dev.mikoto2000.study.springboot.mybatis.firststep.domain.entity.User;
import dev.mikoto2000.study.springboot.mybatis.firststep.domain.entity.UserSearchRequest;
import dev.mikoto2000.study.springboot.mybatis.firststep.domain.repository.UserMapper;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> searchAll() {
        return userMapper.searchAll();
    }

    public Map<Long, User> searchAllMap() {
        return userMapper.searchAllMap();
    }

    public User search(Long id) {
        UserSearchRequest userSearchRequest = new UserSearchRequest(id);
        return userMapper.search(userSearchRequest);
    }

    public int insert(long id, String name) {
        return userMapper.insert(id, name);
    }

    public void delete(long id) {
        userMapper.delete(id);
    }
}
