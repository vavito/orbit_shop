package com.orbit_shop.product.mapper;

import com.orbit_shop.product.domain.Product;
import com.orbit_shop.product.dto.ProductRequestDTO;
import com.orbit_shop.product.dto.ProductResponseDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private final ProductMapper mapper = new ProductMapper();

    @Test
    void shouldMapRequestDtoToEntity() {
        ProductRequestDTO dto = new ProductRequestDTO("Cadeira", "Ergonômica", new BigDecimal("890.00"), 5);

        Product entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.name(), entity.getName());
        assertEquals(dto.description(), entity.getDescription());
        assertEquals(dto.price(), entity.getPrice());
        assertEquals(dto.quantity(), entity.getQuantity());
    }

    @Test
    void shouldMapEntityToResponseDto() {
        Product entity = Product.create("Cadeira", "Ergonômica", new BigDecimal("890.00"), 5);

        ProductResponseDTO response = mapper.toResponse(entity);

        assertNotNull(response);
        assertEquals(entity.getName(), response.name());
        assertEquals(entity.getPrice(), response.price());
    }

    @Test
    void shouldApplyChangesToEntity() {
        Product entity = Product.create("Nome Antigo", "Desc Antiga", new BigDecimal("10.00"), 2);
        ProductRequestDTO dto = new ProductRequestDTO("Nome Novo", "Desc Nova", new BigDecimal("20.00"), 10);

        mapper.applyChanges(entity, dto);

        assertEquals("Nome Novo", entity.getName());
        assertEquals("Desc Nova", entity.getDescription());
        assertEquals(new BigDecimal("20.00"), entity.getPrice());
        assertEquals(10, entity.getQuantity());
    }
}