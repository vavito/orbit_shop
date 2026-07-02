package com.orbit_shop.order.dto;

import com.orbit_shop.order.domain.OrderStatus;
import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        Long customerId,
        String deliveryAddress,
        BigDecimal totalPrice,
        OrderStatus status,
        List<OrderItemResponseDTO> items
) {}

