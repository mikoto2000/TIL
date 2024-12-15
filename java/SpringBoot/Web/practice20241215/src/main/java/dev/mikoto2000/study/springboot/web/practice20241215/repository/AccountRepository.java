package dev.mikoto2000.study.springboot.web.practice20241215.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import dev.mikoto2000.study.springboot.web.practice20241215.entity.Account;

/**
 * AccountRepository
 */
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
}

