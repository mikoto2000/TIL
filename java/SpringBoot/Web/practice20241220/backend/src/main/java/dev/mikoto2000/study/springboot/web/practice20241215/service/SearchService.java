package dev.mikoto2000.study.springboot.web.practice20241215.service;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.mikoto2000.study.springboot.web.practice20241215.repository.SearchRepository;

/**
 * SearchService
 */
@Service
public class SearchService {
  private SearchRepository searchRepository;

  /**
   * Constructor
   */
  public SearchService(SearchRepository searchRepository) {
    this.searchRepository = searchRepository;
  }

  public Page<Map<String, Object>> searchBookMaster(
      Long id,
      String name,
      LocalDate publicationDateBegin,
      LocalDate publicationDateEnd,
      String ndcCategoryName,
      Pageable pageable) {
    return this.searchRepository.searchBookMaster(id, name, publicationDateBegin, publicationDateEnd, ndcCategoryName, pageable);
  }
}
