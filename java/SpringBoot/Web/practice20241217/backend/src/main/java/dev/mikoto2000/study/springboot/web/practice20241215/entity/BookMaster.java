package dev.mikoto2000.study.springboot.web.practice20241215.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BookMaster
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookMaster {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  private String name;
  @NotNull
  private LocalDate publicationDate;
  @NotNull
  @ManyToOne
  private NdcCategory ndcCategory;
  @ManyToMany
  private List<Author> author;
}

