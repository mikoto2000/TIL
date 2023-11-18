package dev.mikoto2000.study.springboot.data.rest.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.mikoto2000.study.springboot.data.rest.example.entity.AccountLazyAndExported;

@RepositoryRestResource(collectionResourceRel = "accounts_lazy_and_exported", path = "accounts_lazy_and_exported")
public interface AccountLazyAndExportedRepository extends JpaRepository<AccountLazyAndExported, Long> {
    
}
