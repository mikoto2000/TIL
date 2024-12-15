package dev.mikoto2000.study.springboot.web.practice20241215.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Roles
 */
@Entity
@Data
@Table(name = "roles")
public class Role {
  @Id
  Long id;
  String name;
}
