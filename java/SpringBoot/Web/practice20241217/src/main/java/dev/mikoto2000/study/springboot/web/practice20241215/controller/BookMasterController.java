package dev.mikoto2000.study.springboot.web.practice20241215.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.mikoto2000.study.springboot.web.practice20241215.entity.Author;
import dev.mikoto2000.study.springboot.web.practice20241215.entity.BookMaster;
import dev.mikoto2000.study.springboot.web.practice20241215.entity.BookMasterAuthorRelationship;
import dev.mikoto2000.study.springboot.web.practice20241215.model.CreateBookMasterRequestValue;
import dev.mikoto2000.study.springboot.web.practice20241215.model.CreateBookMasterResponseValue;
import dev.mikoto2000.study.springboot.web.practice20241215.repository.AuthorRepository;
import dev.mikoto2000.study.springboot.web.practice20241215.repository.BookMasterRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * BookMasterController
 */
@RestController
@Slf4j
public class BookMasterController {

  private AuthorRepository authorRepository;
  private BookMasterRepository bookMasterRepository;

  public BookMasterController(
      BookMasterRepository bookMasterRepository,
      AuthorRepository authorRepository
      ) {
    this.bookMasterRepository = bookMasterRepository;
    this.authorRepository = authorRepository;
  }

  @PostMapping("/dummy")
  public CreateBookMasterResponseValue createBookMaster(@RequestBody CreateBookMasterRequestValue params) {

    log.debug("params: {}", params);

    // BookMaster 生成
    final BookMaster bm0 = new BookMaster(null, params.getName(), null);
    log.debug("bm: {}", bm0);
    final BookMaster bm = this.bookMasterRepository.save(bm0);
    log.debug("saved bm: {}", bm);

    // AuthorIds から紐づける Author の配列を取得
    Iterable<Author> authorsIter = authorRepository.findAllById(params.getAuthorIds());
    List<Author> authors = StreamSupport.stream(authorsIter.spliterator(), false)
        .collect(Collectors.toList());

    // Author の配列から BookMasterAuthorRelationship の配列を取得
    var authorsRel = authors.stream().map((e) -> new BookMasterAuthorRelationship(null, bm, e)).collect(Collectors.toList());

    bm.setBookMasterAuthorRelationship(authorsRel);
    bookMasterRepository.save(bm);

    return new CreateBookMasterResponseValue(bm.getId(), bm.getName(), Arrays.asList());
  }

}
