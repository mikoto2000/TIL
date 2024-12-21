package dev.mikoto2000.study.springboot.web.practice20241215.model;

import java.util.List;

import dev.mikoto2000.study.springboot.web.practice20241215.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookMasterResponseValue {
  private Long id;
  private String name;
  private List<Author> authors;
}


