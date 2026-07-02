package com.orbit_shop.order.dto;

import java.util.List;

public record OrderRequestDTO(
        Long customerId,
        String deliveryAddress,
        List<OrderItemRequestDTO> items
) {}

