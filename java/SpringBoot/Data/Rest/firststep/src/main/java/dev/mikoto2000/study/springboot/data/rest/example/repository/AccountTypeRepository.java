package dev.mikoto2000.study.springboot.data.rest.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.mikoto2000.study.springboot.data.rest.example.entity.AccountType;

@RepositoryRestResource(collectionResourceRel = "accountType", path = "account_type")
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
    
}
