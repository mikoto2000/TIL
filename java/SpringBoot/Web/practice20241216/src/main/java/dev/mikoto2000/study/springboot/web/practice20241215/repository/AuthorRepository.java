package dev.mikoto2000.study.springboot.web.practice20241215.repository;

import org.springframework.data.repository.CrudRepository;

import dev.mikoto2000.study.springboot.web.practice20241215.entity.Author;

/**
 * AuthorRepository
 */
public interface AuthorRepository extends CrudRepository<Author, Long> {
}

