package com.orbit_shop.product.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(

<<<<<<< HEAD
        Integer id,
=======
        Long id,
>>>>>>> 9692f112925f9012e909702f67b8ac965ad528ce
        String name,
        String description,
        BigDecimal price,
        int quantity

) {}