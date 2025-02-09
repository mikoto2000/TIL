package dev.mikoto2000.study.mybatis.xmlfirststep.dto;

import java.util.List;

import lombok.Data;

/**
 * User
 */
@Data
public class User {
  private long id;
  private String name;
  private List<Author> authors;
}

