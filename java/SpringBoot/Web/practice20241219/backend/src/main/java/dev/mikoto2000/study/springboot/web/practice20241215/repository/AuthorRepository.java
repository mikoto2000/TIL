package dev.mikoto2000.study.springboot.web.practice20241215.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.mikoto2000.study.springboot.web.practice20241215.entity.Author;
import dev.mikoto2000.study.springboot.web.practice20241215.projection.DefaultAuthorProjection;

/**
 * AuthorRepository
 */
@RepositoryRestResource(excerptProjection = DefaultAuthorProjection.class)
public interface AuthorRepository extends PagingAndSortingRepository<Author, Long>, CrudRepository<Author, Long> {

  @Query(value = """
    select a from Author a
      where
        a.id = :id or :id is null
        and
        a.name = :name or :name is null
  """
  )
  Page<Author> findByComplexConditions(Long id, String name, Pageable pageable);
}

