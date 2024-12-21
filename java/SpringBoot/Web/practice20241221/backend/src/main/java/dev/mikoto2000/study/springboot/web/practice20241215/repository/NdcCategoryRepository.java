package dev.mikoto2000.study.springboot.web.practice20241215.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.mikoto2000.study.springboot.web.practice20241215.entity.NdcCategory;
import dev.mikoto2000.study.springboot.web.practice20241215.projection.DefaultNdcCategoryProjection;

/**
 * NdcCategoryRepository
 */
@RepositoryRestResource(excerptProjection = DefaultNdcCategoryProjection.class)
public interface NdcCategoryRepository
    extends PagingAndSortingRepository<NdcCategory, Long>, CrudRepository<NdcCategory, Long> {
}
