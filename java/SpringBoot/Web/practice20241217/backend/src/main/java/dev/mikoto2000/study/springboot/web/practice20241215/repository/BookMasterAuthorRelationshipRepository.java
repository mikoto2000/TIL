package dev.mikoto2000.study.springboot.web.practice20241215.repository;

import org.springframework.data.repository.CrudRepository;

import dev.mikoto2000.study.springboot.web.practice20241215.entity.BookMasterAuthorRelationship;

/**
 * BookMasterAuthorRelationshipRepository
 */
public interface BookMasterAuthorRelationshipRepository extends CrudRepository<BookMasterAuthorRelationship, Long> {
}

