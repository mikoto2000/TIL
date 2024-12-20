package dev.mikoto2000.study.springboot.web.practice20241215.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mikoto2000.study.springboot.web.practice20241215.service.SearchService;

/**
 * EchoController
 */
@RestController
public class EchoController {

  private SearchService searchService;

  /**
   * Constructor
   */
  public EchoController(SearchService searchService) {
    this.searchService = searchService;
  }

  @GetMapping(path = "/hello")
  public String hello() {
    return "Hello, World!";
  }

  @GetMapping(path = "/searchBookMasters")
  public Page<Map<String, Object>> searchBookMasters(
      @Nullable Long id,
      @Nullable String name,
      @Nullable LocalDate publicationDateBegin,
      @Nullable LocalDate publicationDateEnd,
      @Nullable String ndcCategoryName,
      @Nullable @ParameterObject Pageable pageable) {
    return this.searchService.searchBookMaster(id, name, publicationDateBegin, publicationDateEnd, ndcCategoryName, pageable);
  }
}
