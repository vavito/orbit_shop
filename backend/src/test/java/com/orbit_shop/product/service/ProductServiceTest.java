package com.orbit_shop.product.service;

import com.orbit_shop.product.domain.Product;
import com.orbit_shop.product.dto.ProductRequestDTO;
import com.orbit_shop.product.dto.ProductResponseDTO;
import com.orbit_shop.product.mapper.ProductMapper;
import com.orbit_shop.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Spy // Usamos Spy aqui para testar a conversão real do mapper sem precisar mocar cada passo
    private ProductMapper mapper;

    @InjectMocks
    private ProductService service;

    @Test
    void shouldCreateProductSuccessfully() {
        ProductRequestDTO dto = new ProductRequestDTO("Fone", "Bluetooth", new BigDecimal("150.00"), 10);
        Product product = Product.create("Fone", "Bluetooth", new BigDecimal("150.00"), 10);

        when(repository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO response = service.createProduct(dto);

        assertNotNull(response);
        assertEquals("Fone", response.name());
        verify(repository).save(any(Product.class));
    }

    @Test
    void shouldGetProductByIdSuccessfully() {
        Product product = Product.create("Fone", "Bluetooth", new BigDecimal("150.00"), 10);
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDTO response = service.getProductById(1L);

        assertNotNull(response);
        assertEquals("Fone", response.name());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.getProductById(1L));
        assertEquals("Produto não encontrado", exception.getMessage());
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.deleteProduct(1L));

        verify(repository).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentProduct() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.deleteProduct(1L));
        verify(repository, never()).deleteById(any());
    }
}