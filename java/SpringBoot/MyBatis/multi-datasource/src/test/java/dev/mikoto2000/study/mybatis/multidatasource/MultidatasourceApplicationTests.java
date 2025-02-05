package dev.mikoto2000.study.mybatis.multidatasource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.mikoto2000.study.mybatis.multidatasource.primary.PrimaryUserMapper;
import dev.mikoto2000.study.mybatis.multidatasource.secondary.SecondaryUserMapper;

@SpringBootTest
class MultidatasourceApplicationTests {

  @Autowired
  private PrimaryUserMapper pMapper;

  @Autowired
  private SecondaryUserMapper sMapper;

  @Test
  void contextLoads() {
    System.out.println(pMapper.findAll());
    System.out.println(sMapper.findAll());
  }

}
