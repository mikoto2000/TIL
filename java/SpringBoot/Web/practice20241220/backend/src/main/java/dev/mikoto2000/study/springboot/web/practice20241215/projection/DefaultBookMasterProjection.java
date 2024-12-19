package dev.mikoto2000.study.springboot.web.practice20241215.projection;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import dev.mikoto2000.study.springboot.web.practice20241215.entity.Author;
import dev.mikoto2000.study.springboot.web.practice20241215.entity.BookMaster;
import dev.mikoto2000.study.springboot.web.practice20241215.entity.NdcCategory;

/**
 * DefaultBookMasterProjection
 */
@Projection(name = "defaultBookMasterProjection", types = { BookMaster.class })
public interface DefaultBookMasterProjection {
  @Value("#{target.id}")
  Long getId();
  String getName();
  LocalDate getPublicationDate();
  NdcCategory getNdcCategory();
  List<Author> getAuthor();
}


