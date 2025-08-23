package dev.mikoto2000.study.springboot.mybatis.firststep.domain.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserSearchResult
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchResult {
  private Long id;
  private String name;
  private List<UserDetail> userDetails;
}

