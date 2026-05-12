package com.orbit_shop.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequestDTO(

        @NotBlank(message = "Nome não pode ser vazio")
        String name,

        String description,

        @NotNull(message = "Preço é obrigatório")
        @PositiveOrZero(message = "Preço deve ser zero ou positivo")
        BigDecimal price,

        @PositiveOrZero(message = "Quantidade deve ser zero ou maior")
        int quantity

) {}