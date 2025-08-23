package dev.mikoto2000.study.springboot.mybatis.firststep.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import dev.mikoto2000.study.springboot.mybatis.firststep.domain.entity.User;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFindAll() {
      var users = userMapper.findAllUserDetails();

      String expect = "[UserSearchResult(id=1, name=user1, userDetails=[UserDetail(id=null, userId=1, description=user1 info1), UserDetail(id=null, userId=1, description=user1 info2), UserDetail(id=null, userId=1, description=user1 info3)]), UserSearchResult(id=2, name=user2, userDetails=[UserDetail(id=null, userId=2, description=user2 info1), UserDetail(id=null, userId=2, description=user2 info2), UserDetail(id=null, userId=2, description=user2 info3)])]";
      assertEquals(expect, users.toString());
    }
}

