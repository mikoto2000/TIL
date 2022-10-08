package dev.mikoto2000.study.springboot.mybatis.firststep.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import dev.mikoto2000.study.springboot.mybatis.firststep.domain.entity.User;
import dev.mikoto2000.study.springboot.mybatis.firststep.domain.entity.UserSearchRequest;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "testSearch.sql")
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSearch() {
        var user10SearchRequest = new UserSearchRequest(10L);
        User user10 = this.userMapper.search(user10SearchRequest);

        assertEquals(10, user10.getId());
        assertEquals("user10", user10.getName());

        var userSearchRequest_Invalid = new UserSearchRequest(1L);
        User invalidUser = this.userMapper.search(userSearchRequest_Invalid);

        assertNull(invalidUser);
    }
}

