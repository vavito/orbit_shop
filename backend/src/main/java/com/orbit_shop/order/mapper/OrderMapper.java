package com.orbit_shop.order.mapper;

import com.orbit_shop.order.domain.Order;
import com.orbit_shop.order.dto.OrderResponseDTO;
import com.orbit_shop.order.dto.OrderItemResponseDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponseDTO toResponseDTO(Order order) {
        var items = order.getItems().stream()
                .map(item -> new OrderItemResponseDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());

        return new OrderResponseDTO(
                order.getId(),
                order.getCustomer().getId(),
                order.getDeliveryAddress(),
                order.getTotalPrice(),
                order.getStatus(),
                items
        );
    }
}