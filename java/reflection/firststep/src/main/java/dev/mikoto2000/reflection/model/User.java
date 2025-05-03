package dev.mikoto2000.reflection.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private String name;
  private Integer age;
  private String email;
}

