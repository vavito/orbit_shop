package com.orbit_shop.order.domain;

import com.orbit_shop.product.domain.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private BigDecimal subtotal;

    public static OrderItem create(
            Integer quantity,
            Product product
    ) {

        if (product == null) {
            throw new IllegalArgumentException(
                    "Produto não pode ser vazio."
            );
        }

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantidade deve ser maior que zero."
            );
        }

        OrderItem orderItem = new OrderItem();

        orderItem.product = product;
        orderItem.quantity = quantity;
        orderItem.unitPrice = product.getPrice();
        orderItem.calculateSubtotal();

        return orderItem;
    }

    void assignOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException(
                    "Pedido não pode ser vazio."
            );
        }

        this.order = order;
    }

    void removeOrder() {
        this.order = null;
    }

    void increaseQuantity(Integer amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException(
                    "Quantidade deve ser maior que zero."
            );
        }

        this.quantity += amount;
        calculateSubtotal();
    }

    void decreaseQuantity(Integer amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException(
                    "Quantidade deve ser maior que zero."
            );
        }

        if (amount >= this.quantity) {
            throw new IllegalArgumentException(
                    "A quantidade resultante deve ser maior que zero. "
                            + "Para remover o item, remova-o do pedido."
            );
        }

        this.quantity -= amount;
        calculateSubtotal();
    }

    void redefineQuantity(Integer newAmount) {
        if (newAmount == null || newAmount <= 0) {
            throw new IllegalArgumentException(
                    "Quantidade deve ser maior que zero."
            );
        }

        this.quantity = newAmount;
        calculateSubtotal();
    }

    private void calculateSubtotal() {
        this.subtotal = unitPrice.multiply(
                BigDecimal.valueOf(quantity)
        );
    }
}