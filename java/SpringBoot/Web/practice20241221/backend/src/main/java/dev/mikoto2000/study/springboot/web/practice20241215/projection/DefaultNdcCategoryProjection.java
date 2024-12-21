package dev.mikoto2000.study.springboot.web.practice20241215.projection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import dev.mikoto2000.study.springboot.web.practice20241215.entity.NdcCategory;

/**
 * DefaultNdcCategoryProjection
 */
@Projection(name = "defaultNdcCategoryProjection", types = { NdcCategory.class })
public interface DefaultNdcCategoryProjection {
  @Value("#{target.id}")
  Long getId();
  String getName();
  String getNumber();
}

