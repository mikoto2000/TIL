package dev.mikoto2000.study.springboot.mybatis.firststep.domain.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import dev.mikoto2000.study.springboot.mybatis.firststep.domain.entity.User;
import dev.mikoto2000.study.springboot.mybatis.firststep.domain.entity.UserSearchRequest;

@Mapper
public interface UserMapper {
    @Select("SELECT id, name FROM user1")
    List<User> searchAll();

    @Select("SELECT id, name FROM user1 WHERE id = #{id}")
    User search(UserSearchRequest user);
}

