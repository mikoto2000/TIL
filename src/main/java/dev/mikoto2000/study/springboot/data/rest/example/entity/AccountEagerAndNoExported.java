package dev.mikoto2000.study.springboot.data.rest.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

@Data
@Entity
@Table(name = "account")
public class AccountEagerAndNoExported {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @RestResource(exported = false)
    @JoinColumn(name = "account_type_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private AccountType accountType;
}
