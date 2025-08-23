package dev.mikoto2000.study.springboot.mybatis.firststep.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDetail
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {
  private Long id;
  private Long userId;
  private String description;
}

