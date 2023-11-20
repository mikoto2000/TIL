package dev.mikoto2000.study.springboot.data.rest.example.projection;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import dev.mikoto2000.study.springboot.data.rest.example.entity.Item;
import dev.mikoto2000.study.springboot.data.rest.example.entity.Order;

@Projection(name = "OrderProjectionDefault", types = { Order.class })
public interface OrderProjectionDefault {
    Long getId();

    String getOrderName();

    // ＠Value で、対象エンティティのどのメソッドをここに紐づけるかを設定
    @Value("#{target.getItems()}")
    List<Item> getItems();
}
