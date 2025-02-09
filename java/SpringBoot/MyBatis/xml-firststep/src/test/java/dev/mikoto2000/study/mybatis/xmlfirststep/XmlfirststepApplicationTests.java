package dev.mikoto2000.study.mybatis.xmlfirststep;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.mikoto2000.study.mybatis.xmlfirststep.mapper.UserMapper;

@SpringBootTest
class XmlfirststepApplicationTests {

  @Autowired
  private UserMapper userMapper;

	@Test
	void userMapper() {
    var users = userMapper.findAll();

    System.out.println(users);
	}

}
