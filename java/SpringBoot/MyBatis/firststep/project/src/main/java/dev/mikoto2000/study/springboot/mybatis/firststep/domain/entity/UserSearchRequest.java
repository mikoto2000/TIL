package dev.mikoto2000.study.springboot.mybatis.firststep.domain.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class UserSearchRequest implements Serializable {
  private Long id;
}


