package com.orbit_shop.product.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldCreateProductWithValidData() {
        Product product = Product.create("Monitor", "24 polegadas", new BigDecimal("1200.00"), 15);

        assertNotNull(product);
        assertEquals("Monitor", product.getName());
        assertEquals(new BigDecimal("1200.00"), product.getPrice());
        assertEquals(15, product.getQuantity());
    }

    @Test
    void shouldThrowExceptionWhenCreatingWithInvalidName() {
        assertThrows(IllegalArgumentException.class, () ->
                Product.create("", "Descrição", new BigDecimal("10.00"), 5)
        );
    }

    @Test
    void shouldThrowExceptionWhenCreatingWithNegativePrice() {
        assertThrows(IllegalArgumentException.class, () ->
                Product.create("Nome", "Descrição", new BigDecimal("-1.00"), 5)
        );
    }

    @Test
    void shouldIncreaseStockSuccessfully() {
        Product product = Product.create("Item", "Desc", new BigDecimal("10.00"), 10);
        product.increaseStock(5);
        assertEquals(15, product.getQuantity());
    }

    @Test
    void shouldDecreaseStockSuccessfully() {
        Product product = Product.create("Item", "Desc", new BigDecimal("10.00"), 10);
        product.decreaseStock(4);
        assertEquals(6, product.getQuantity());
    }

    @Test
    void shouldThrowExceptionWhenStockIsInsufficient() {
        Product product = Product.create("Item", "Desc", new BigDecimal("10.00"), 5);

        assertThrows(IllegalStateException.class, () -> product.decreaseStock(6));
    }
}