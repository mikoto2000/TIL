package dev.mikoto2000.study.springboot.web.practice20241215.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Account
 */
@Entity
@Data
@Table(name = "accounts")
public class Account {
  @Id
  private Long id;
  private String name;

  @ManyToOne
  private Role role;
}

