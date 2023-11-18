package dev.mikoto2000.study.springboot.data.rest.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.mikoto2000.study.springboot.data.rest.example.entity.Order;
import dev.mikoto2000.study.springboot.data.rest.example.projection.OrderProjectionDefault;

@RepositoryRestResource(excerptProjection = OrderProjectionDefault.class)
public interface OrderRepository extends JpaRepository<Order, Long> {

}
