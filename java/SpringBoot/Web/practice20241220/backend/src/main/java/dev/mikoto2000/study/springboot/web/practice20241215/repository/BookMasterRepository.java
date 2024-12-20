package dev.mikoto2000.study.springboot.web.practice20241215.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.mikoto2000.study.springboot.web.practice20241215.entity.BookMaster;
import dev.mikoto2000.study.springboot.web.practice20241215.projection.DefaultBookMasterProjection;

/**
 * BookMasterRepository
 */
@RepositoryRestResource(excerptProjection = DefaultBookMasterProjection.class)
public interface BookMasterRepository
    extends PagingAndSortingRepository<BookMaster, Long>, CrudRepository<BookMaster, Long> {
}
