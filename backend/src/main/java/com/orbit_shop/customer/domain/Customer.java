package com.orbit_shop.customer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CollectionIdJavaClass;

import javax.annotation.processing.Generated;

@Table(name = "customer")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    // Define que o campo é a chave primária da sua entidade Customer
    @Id
    // Define que o banco de dados gerará o ID de forma automática/incremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;
}
