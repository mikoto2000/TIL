package dev.mikoto2000.study.springboot.data.rest.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.mikoto2000.study.springboot.data.rest.example.entity.Item;
import dev.mikoto2000.study.springboot.data.rest.example.projection.ItemProjectionDefault;

@RepositoryRestResource(excerptProjection = ItemProjectionDefault.class)
public interface ItemRepository extends JpaRepository<Item, Long> {
    
}
