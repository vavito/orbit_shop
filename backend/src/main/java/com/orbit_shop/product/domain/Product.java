package com.orbit_shop.product.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;

    public static Product create(String name, String description, BigDecimal price, int quantity) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço inválido");
        }

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        Product product = new Product();
        product.name = name;
        product.description = description;
        product.price = price;
        product.quantity = quantity;

        return product;
    }

    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }
        this.name = name;
    }

    public void changePrice(BigDecimal newPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço inválido");
        }
        this.price = newPrice;
    }

    public void changeDescription(String newDescription) {
        this.description = newDescription;
    }

    public void increaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }
        this.quantity += amount;
    }

    public void decreaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }
        if (this.quantity < amount) {
            throw new IllegalStateException("Estoque insuficiente");
        }
        this.quantity -= amount;
    }

    public void redefineStock(int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("A quantidade de estoque não pode ser negativa");
        }
        this.quantity = newQuantity;
    }
}