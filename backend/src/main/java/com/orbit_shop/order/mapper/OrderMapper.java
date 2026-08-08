package com.orbit_shop.order.mapper;

import com.orbit_shop.customer.domain.Customer;
import com.orbit_shop.order.domain.Order;
import com.orbit_shop.order.domain.OrderItem;
import com.orbit_shop.order.dto.OrderItemRequestDTO;
import com.orbit_shop.order.dto.OrderItemResponseDTO;
import com.orbit_shop.order.dto.OrderRequestDTO;
import com.orbit_shop.order.dto.OrderResponseDTO;
import com.orbit_shop.product.domain.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderResponseDTO toOrderResponseDTO(Order order) {
        if (order == null) {
            return null;
        }

        List<OrderItemResponseDTO> items = order.getItems()
                .stream()
                .map(this::toOrderItemResponseDTO)
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getCustomer().getId(),
                order.getDeliveryAddress(),
                order.getTotalPrice(),
                order.getStatus(),
                items
        );
    }

    public OrderItemResponseDTO toOrderItemResponseDTO(
            OrderItem orderItem
    ) {
        if (orderItem == null) {
            return null;
        }

        return new OrderItemResponseDTO(
                orderItem.getId(),
                orderItem.getProduct().getId(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                orderItem.getSubtotal()
        );
    }

    public Order toOrderEntity(
            Customer customer,
            OrderRequestDTO dto
    ) {
        if (dto == null || customer == null) {
            return null;
        }

        return Order.create(
                customer,
                dto.deliveryAddress()
        );
    }

    public OrderItem toOrderItemEntity(
            OrderItemRequestDTO dto,
            Product product
    ) {
        if (dto == null || product == null) {
            return null;
        }

        return OrderItem.create(
                dto.quantity(),
                product
        );
    }
}