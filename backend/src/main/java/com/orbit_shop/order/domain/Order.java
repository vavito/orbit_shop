package com.orbit_shop.order.domain;

import com.orbit_shop.customer.domain.Customer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Getter(AccessLevel.NONE)
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false)
    private String deliveryAddress;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    public static Order create(
            Customer customer,
            String deliveryAddress
    ) {

        if (customer == null) {
            throw new IllegalArgumentException(
                    "É necessário informar um cliente para criar um pedido."
            );
        }

        if (deliveryAddress == null || deliveryAddress.isBlank()) {
            throw new IllegalArgumentException(
                    "O endereço não pode ser vazio."
            );
        }

        Order order = new Order();

        order.customer = customer;
        order.deliveryAddress = deliveryAddress;
        order.totalPrice = BigDecimal.ZERO;
        order.status = OrderStatus.PENDING;

        return order;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "Item não pode ser vazio."
            );
        }

        items.add(item);
        item.assignOrder(this);

        calculateTotalPrice();
    }

    public void removeItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "Item não pode ser vazio."
            );
        }

        if (!items.contains(item)) {
            throw new IllegalArgumentException(
                    "O item não pertence a este pedido."
            );
        }

        items.remove(item);
        item.removeOrder();

        calculateTotalPrice();
    }

    public void increaseItemQuantity(
            OrderItem item,
            Integer amount
    ) {
        validateItemBelongsToOrder(item);

        item.increaseQuantity(amount);
        calculateTotalPrice();
    }

    public void decreaseItemQuantity(
            OrderItem item,
            Integer amount
    ) {
        validateItemBelongsToOrder(item);

        item.decreaseQuantity(amount);
        calculateTotalPrice();
    }

    public void redefineItemQuantity(
            OrderItem item,
            Integer quantity
    ) {
        validateItemBelongsToOrder(item);

        item.redefineQuantity(quantity);
        calculateTotalPrice();
    }

    public void updateStatus(OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException(
                    "Status não pode ser vazio."
            );
        }

        if (this.status == status) {
            throw new IllegalArgumentException(
                    "O pedido já possui o status de " + status
            );
        }

        this.status = status;
    }

    private void validateItemBelongsToOrder(OrderItem item) {
        if (item == null || !items.contains(item)) {
            throw new IllegalArgumentException(
                    "O item não pertence a este pedido."
            );
        }
    }

    private void calculateTotalPrice() {
        this.totalPrice = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}