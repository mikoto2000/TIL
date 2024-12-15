package dev.mikoto2000.study.springboot.web.practice20241215.repository;

import org.springframework.data.repository.CrudRepository;

import dev.mikoto2000.study.springboot.web.practice20241215.entity.Role;

/**
 * RoleRepository
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
}
