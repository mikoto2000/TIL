package dev.mikoto2000.study.springboot.web.practice20241215.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BookMaster_Author_Relationship
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookMasterAuthorRelationship {
  @Id
  private Long id;
  @OneToOne
  private BookMaster bookMaster;
  @OneToOne
  private Author author;
}

