package dev.mikoto2000.springboot.security.usernameandpassword.firststep.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "accounts")
public class Account {
  @Id
  private String username;
  private String password;
  private String role;
  private boolean enabled;
}
