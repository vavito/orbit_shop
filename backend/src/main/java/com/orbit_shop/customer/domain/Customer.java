package com.orbit_shop.customer.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "customer")
public class Customer {

    @Id
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

    public static Customer create(String name, String email, String cpf, String phone, String password) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome inválido");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email inválido");
        if (cpf == null || cpf.length() != 11) throw new IllegalArgumentException("CPF inválido");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Senha inválida");

        Customer customer = new Customer();
        customer.name = name;
        customer.email = email;
        customer.cpf = cpf;
        customer.phone = phone;
        customer.password = password;
        return customer;
    }

    public void updateName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome inválido");
        this.name = name;
    }

    public void updatePhone(String phone) {
        if (phone == null || phone.isBlank()) throw new IllegalArgumentException("Telefone inválido");
        this.phone = phone;
    }

    public void changePassword(String newPassword) {
        if (newPassword == null || newPassword.isBlank()) throw new IllegalArgumentException("Senha inválida");
        this.password = newPassword;
    }
}
