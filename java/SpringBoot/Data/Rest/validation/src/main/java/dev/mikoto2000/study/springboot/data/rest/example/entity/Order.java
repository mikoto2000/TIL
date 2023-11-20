package dev.mikoto2000.study.springboot.data.rest.example.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "torder")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "torder_seq_generator")
    @SequenceGenerator(name = "torder_seq_generator", sequenceName = "torder_id_seq", allocationSize = 1)
    private Long id;

    private String orderName;

    @JoinTable(
            name = "item_order",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Item> items;

}
