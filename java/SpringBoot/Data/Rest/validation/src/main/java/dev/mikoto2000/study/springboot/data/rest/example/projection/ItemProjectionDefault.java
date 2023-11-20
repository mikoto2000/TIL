package dev.mikoto2000.study.springboot.data.rest.example.projection;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import dev.mikoto2000.study.springboot.data.rest.example.entity.Item;
import dev.mikoto2000.study.springboot.data.rest.example.entity.Order;

@Projection(name = "ItemProjectionDefault", types = { Item.class })
public interface ItemProjectionDefault {
    Long getId();

    String getItemName();

    // ＠Value で、対象エンティティのどのメソッドをここに紐づけるかを設定
    @Value("#{target.getOrders()}")
    List<Order> getOrders();
}
