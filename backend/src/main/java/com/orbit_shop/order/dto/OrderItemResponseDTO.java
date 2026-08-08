package com.orbit_shop.order.dto;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        Long id,
        Long productId,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal
) {
}
