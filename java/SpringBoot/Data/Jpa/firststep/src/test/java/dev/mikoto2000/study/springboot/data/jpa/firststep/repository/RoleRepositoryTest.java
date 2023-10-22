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
  public void testFindAll() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
    Page<Role> page = roleRepository.findAll(pageable);

    var accounts = page.getContent();
    assertEquals(3, accounts.size());
    assertEquals(new Role(3L, "developer", null), accounts.get(0));
    assertEquals(new Role(2L, "user", null), accounts.get(1));
    assertEquals(new Role(1L, "admin", null), accounts.get(2));
  }

  @Test
  @Sql(scripts = "/dev/mikoto2000/study/springboot/data/jpa/firststep/repository/RoleRepositoryTestFindAll.sql")
  public void testFindAllWithPageable() {

    // 1 ページに 1 つずつとした場合の 0 ページ目
    Pageable pageable01 = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"));
    Page<Role> page01 = roleRepository.findAll(pageable01);
    var accounts01 = page01.getContent();
    assertEquals(1, accounts01.size());
    assertEquals(new Role(3L, "developer", null), accounts01.get(0));

    // 1 ページに 1 つずつとした場合の 1 ページ目
    Pageable pageable11 = PageRequest.of(1, 1, Sort.by(Sort.Direction.DESC, "id"));
    Page<Role> page11 = roleRepository.findAll(pageable11);
    var accounts11 = page11.getContent();
    assertEquals(1, accounts11.size());
    assertEquals(new Role(2L, "user", null), accounts11.get(0));

    // 1 ページに 1 つずつとした場合の 2 ページ目
    Pageable pageable21 = PageRequest.of(2, 1, Sort.by(Sort.Direction.DESC, "id"));
    Page<Role> page21 = roleRepository.findAll(pageable21);
    var accounts21 = page21.getContent();
    assertEquals(1, accounts21.size());
    assertEquals(new Role(1L, "admin", null), accounts21.get(0));
  }
}

