package com.orbit_shop.customer.dto;

public record CustomerResponseDTO(
        Long id,
        String name,
        String email,
        String phone
) {
}
