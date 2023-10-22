package dev.mikoto2000.study.springboot.data.jpa.firststep.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import dev.mikoto2000.study.springboot.data.jpa.firststep.entity.Account;
import dev.mikoto2000.study.springboot.data.jpa.firststep.entity.Role;

/**
 * AccountRepositoryTest
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AccountRepositoryTest {

  @Autowired
  private AccountRepository accountRepository;

  @Test
  @Sql(scripts = "/dev/mikoto2000/study/springboot/data/jpa/firststep/repository/AccountRepositoryTestFindAll.sql")
  public void testFindAll() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
    Page<Account> page = accountRepository.findAll(pageable);

    var accounts = page.getContent();
    assertEquals(3, accounts.size());

    assertEqualsForAccount(1L, "mikoto2000", createRole(1L, "admin"), accounts.get(0));
    assertEqualsForAccount(2L, "makoto2000", createRole(2L, "user"), accounts.get(1));
    assertEqualsForAccount(3L, "mokoto2000", createRole(3L, "developer"), accounts.get(2));
  }

  private Role createRole(Long id, String name) {
    var role = new Role();
    role.setId(id);
    role.setName(name);
    return role;
  }

  private void assertEqualsForAccount(Long id, String name, Role role, Account actual) {
    assertEquals(id, actual.getId(), "id が異なります");
    assertEquals(name, actual.getName(), "name が異なります");
    assertEquals(role.getId(), actual.getRole().getId(), "role.id が異なります");
    assertEquals(role.getName(), actual.getRole().getName(), "role.name が異なります");
  }
}
