package dev.mikoto2000.study.springboot.web.practice20241215.projection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import dev.mikoto2000.study.springboot.web.practice20241215.entity.Author;

/**
 * DefaultAuthorProjection
 */
@Projection(name = "defaultAuthorProjection", types = { Author.class })
public interface DefaultAuthorProjection {
  @Value("#{target.id}")
  Long getId();
  String getName();
}

