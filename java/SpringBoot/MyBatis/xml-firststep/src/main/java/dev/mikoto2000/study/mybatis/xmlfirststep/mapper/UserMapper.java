package dev.mikoto2000.study.mybatis.xmlfirststep.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import dev.mikoto2000.study.mybatis.xmlfirststep.dto.User;

/**
 * UserMapper
 */
@Mapper
public interface UserMapper {
  List<User> findAll();
  User findById(long id);
}

