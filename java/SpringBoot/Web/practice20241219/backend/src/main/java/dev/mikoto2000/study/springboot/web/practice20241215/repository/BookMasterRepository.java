package dev.mikoto2000.study.springboot.web.practice20241215.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.mikoto2000.study.springboot.web.practice20241215.entity.BookMaster;
import dev.mikoto2000.study.springboot.web.practice20241215.projection.DefaultBookMasterProjection;

/**
 * BookMasterRepository
 */
@RepositoryRestResource(excerptProjection = DefaultBookMasterProjection.class)
public interface BookMasterRepository extends PagingAndSortingRepository<BookMaster, Long>, CrudRepository<BookMaster, Long> {

  @Query(value = """
    select b from BookMaster b
      where
        (b.id = :id or :id is null)
        and
        (b.name = :name or :name is null)
        and
        (b.publicationDate >= :publicationDateBegin or :publicationDateBegin is null)
        and
        (b.publicationDate <= :publicationDateEnd or :publicationDateEnd is null)
  """
  )
  Page<BookMaster> findByComplexConditions(
      Long id,
      String name,
      LocalDate publicationDateBegin,
      LocalDate publicationDateEnd,
      Pageable pageable);
}


