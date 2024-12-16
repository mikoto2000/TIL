package dev.mikoto2000.study.springboot.web.practice20241215.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import lombok.Data;

/**
 * BookMaster
 */
@Entity
@Data
public class BookMaster {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @OneToMany
  @JoinTable(
    name = "book_master_author_relationship",
    joinColumns = {
      @JoinColumn(name="author_id", referencedColumnName="id")
    },
    inverseJoinColumns = {
      @JoinColumn(name="book_master_id", referencedColumnName="id")
    }
  )
  private List<BookMasterAuthorRelationship> bookMasterAuthorRelationship;
}


