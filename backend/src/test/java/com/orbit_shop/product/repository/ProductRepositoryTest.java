package com.orbit_shop.product.repository;

import com.orbit_shop.product.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @Mock
    private ProductRepository repository;

    @Test
    void shouldPersistProductSuccessfully() {
        // Arrange
        Product product = Product.create("Notebook", "i7 16GB", new BigDecimal("4500.00"), 5);

        // Como o ID é gerado pelo banco e a entidade não tem setter, usamos o ReflectionTestUtils para simular o ID persistido
        Product savedProduct = Product.create("Notebook", "i7 16GB", new BigDecimal("4500.00"), 5);
        ReflectionTestUtils.setField(savedProduct, "id", 1L);

        when(repository.save(any(Product.class))).thenReturn(savedProduct);

        // Act
        Product saved = repository.save(product);

        // Assert
        assertNotNull(saved.getId());
        assertEquals(1L, saved.getId());
        assertEquals("Notebook", saved.getName());
        verify(repository).save(product);
    }

    @Test
    void shouldFindProductById() {
        // Arrange
        Product product = Product.create("Notebook", "i7 16GB", new BigDecimal("4500.00"), 5);
        ReflectionTestUtils.setField(product, "id", 1L);

        when(repository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        Optional<Product> found = repository.findById(1L);

        // Assert
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
        assertEquals("Notebook", found.get().getName());
        verify(repository).findById(1L);
    }
}