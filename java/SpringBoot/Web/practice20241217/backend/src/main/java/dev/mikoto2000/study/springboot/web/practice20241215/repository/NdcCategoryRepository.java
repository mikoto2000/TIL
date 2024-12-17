package dev.mikoto2000.study.springboot.web.practice20241215.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import dev.mikoto2000.study.springboot.web.practice20241215.entity.NdcCategory;

/**
 * NdcCategoryRepository
 */
public interface NdcCategoryRepository extends PagingAndSortingRepository<NdcCategory, Long>, CrudRepository<NdcCategory, Long> {
}


