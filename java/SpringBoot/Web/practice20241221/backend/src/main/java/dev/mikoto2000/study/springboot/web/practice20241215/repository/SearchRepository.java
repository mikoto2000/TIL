package dev.mikoto2000.study.springboot.web.practice20241215.repository;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import dev.mikoto2000.study.springboot.web.practice20241215.entity.BookMaster;

/**
 * SearchRepository
 */
@Repository
public interface SearchRepository extends PagingAndSortingRepository<BookMaster, Long> {

  @Query(value = """
        select
          b.id,
          b.name,
          b.publication_date as "publicationDate",
          string_agg(a.name, ', ') as author,
          n.name as "ndcCategoryName"
        from book_master as b
        left outer join book_master_author as bma on b.id = bma.book_master_id
        left outer join author as a on a.id = bma.author_id
        left outer join ndc_category as n on b.ndc_category_id = n.id
        where
          (b.id = :id or :id is null)
          and
          (b.name like %:name% or cast(:name as varchar) is null)
          and
          (b.publication_date >= :publicationDateBegin or cast(:publicationDateBegin as date) is null)
          and
          (b.publication_date <= :publicationDateEnd or cast(:publicationDateEnd as date) is null)
          and
          (n.name like %:ndcCategoryName% or cast(:ndcCategoryName as varchar) is null)
        group by
          b.id,
          b.name,
          b.publication_date,
          b.ndc_category_id,
          n.name,
          n.number
      """,
      countQuery = """
        select
          count(*)
        from book_master as b
        left outer join book_master_author as bma on b.id = bma.book_master_id
        left outer join author as a on a.id = bma.author_id
        left outer join ndc_category as n on b.ndc_category_id = n.id
        where
          (b.id = :id or :id is null)
          and
          (b.name like %:name% or cast(:name as varchar) is null)
          and
          (b.publication_date >= :publicationDateBegin or cast(:publicationDateBegin as date) is null)
          and
          (b.publication_date <= :publicationDateEnd or cast(:publicationDateEnd as date) is null)
          and
          (n.name like %:ndcCategoryName% or cast(:ndcCategoryName as varchar) is null)
        group by
          b.id,
          b.name,
          b.publication_date,
          b.ndc_category_id,
          n.name,
          n.number
      """,
      nativeQuery = true)
  Page<Map<String, Object>> searchBookMaster(
      Long id,
      String name,
      LocalDate publicationDateBegin,
      LocalDate publicationDateEnd,
      String ndcCategoryName,
      Pageable pageable);
}
