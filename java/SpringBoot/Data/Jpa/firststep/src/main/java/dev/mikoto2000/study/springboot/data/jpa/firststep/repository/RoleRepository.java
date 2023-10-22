package dev.mikoto2000.study.springboot.data.jpa.firststep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.mikoto2000.study.springboot.data.jpa.firststep.entity.Role;

/**
 * RoleRepository
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}

