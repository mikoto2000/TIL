package dev.mikoto2000.study.springboot.data.jpa.firststep.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import dev.mikoto2000.study.springboot.data.jpa.firststep.entity.Account;

/**
 * AccountRepository
 */
@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
}
