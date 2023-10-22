package dev.mikoto2000.study.springboot.data.jpa.firststep.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import dev.mikoto2000.study.springboot.data.jpa.firststep.entity.Role;

/**
 * RoleRepositoryTest
 */
@SpringBootTest
public class RoleRepositoryTest {

  @Autowired
  private RoleRepository roleRepository;

  @Test
  @Sql(scripts = "/dev/mikoto2000/study/springboot/data/jpa/firststep/repository/RoleRepositoryTestFindAll.sql")
  public void testFind() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
    Page<Role> page = roleRepository.findAll(pageable);

    var accounts = page.getContent();
    assertEquals(3, accounts.size());
    assertEquals(new Role(3L, "developer", null), accounts.get(0));
    assertEquals(new Role(2L, "user", null), accounts.get(1));
    assertEquals(new Role(1L, "admin", null), accounts.get(2));
  }
}

