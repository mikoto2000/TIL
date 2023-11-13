package dev.mikoto2000.study.springboot.data.rest.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.mikoto2000.study.springboot.data.rest.example.entity.AccountEagerAndExported;

@RepositoryRestResource(collectionResourceRel = "accounts_eager_and_exported", path = "accounts_eager_and_exported")
public interface AccountEagerAndExportedRepository extends JpaRepository<AccountEagerAndExported, Long> {
    
}
