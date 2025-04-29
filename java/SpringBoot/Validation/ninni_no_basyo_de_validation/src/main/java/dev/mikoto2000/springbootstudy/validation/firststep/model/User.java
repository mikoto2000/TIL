package dev.mikoto2000.springbootstudy.validation.firststep.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.AllArgsConstructor;

/**
 * User
 */
@Data
@AllArgsConstructor
public class User {
  @NotEmpty
  private String id;

  @NotEmpty
  private String name;

  @NotEmpty
  @Email
  private String email;
}
