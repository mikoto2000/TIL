package dev.mikoto2000.study.springboot.web.practice20241215.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookMasterRequestValue {
  private String name;
  private List<Long> authorIds;
}
