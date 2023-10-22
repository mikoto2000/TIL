package dev.mikoto2000.study.springboot.data.jpa.firststep.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.mikoto2000.study.springboot.data.jpa.firststep.entity.Role;

/**
 * RoleRepositoryTest
 */
@SpringBootTest
public class RoleRepositoryTest {

  @Autowired
  private RoleRepository roleRepository;

  @Test
  public void testFind() {
    List<Role> page = roleRepository.findAll();

    assertEquals(3, page.size());
  }
}

