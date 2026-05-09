package com.orbit_shop.product.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(

        Integer id,
        String name,
        String description,
        BigDecimal price,
        int quantity

) {}