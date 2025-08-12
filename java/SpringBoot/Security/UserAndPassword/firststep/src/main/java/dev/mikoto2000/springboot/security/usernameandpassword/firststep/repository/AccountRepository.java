package dev.mikoto2000.springboot.security.usernameandpassword.firststep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.mikoto2000.springboot.security.usernameandpassword.firststep.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

}
