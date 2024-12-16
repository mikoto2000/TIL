package dev.mikoto2000.study.springboot.web.practice20241215.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.mikoto2000.study.springboot.web.practice20241215.entity.Account;
import dev.mikoto2000.study.springboot.web.practice20241215.projection.DefaultAccountProjection;

/**
 * AccountRepository
 */
@RepositoryRestResource(excerptProjection = DefaultAccountProjection.class)
public interface AccountRepository extends PagingAndSortingRepository<Account, Long>, CrudRepository<Account, Long>{
}

