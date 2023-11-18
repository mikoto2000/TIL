package dev.mikoto2000.study.springboot.data.rest.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.mikoto2000.study.springboot.data.rest.example.entity.AccountLazyAndNoExported;

@RepositoryRestResource(collectionResourceRel = "accounts_lazy_and_no_exported", path = "accounts_lazy_and_no_exported")
public interface AccountLazyAndNoExportedRepository extends JpaRepository<AccountLazyAndNoExported, Long> {
    
}
