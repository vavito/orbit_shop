package com.orbit_shop.product.controller;

import com.orbit_shop.product.dto.ProductRequestDTO;
import com.orbit_shop.product.dto.ProductResponseDTO;
import com.orbit_shop.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService service;

    @InjectMocks
    private ProductController controller;

    @Test
    void shouldCreateProductSuccessfully() {
        // Arrange
        ProductRequestDTO request = new ProductRequestDTO(
                "Teclado Mecânico", "RGB Switch Blue", new BigDecimal("299.90"), 10
        );
        ProductResponseDTO expectedResponse = new ProductResponseDTO(
                1L, "Teclado Mecânico", "RGB Switch Blue", new BigDecimal("299.90"), 10
        );
        when(service.createProduct(any(ProductRequestDTO.class))).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ProductResponseDTO> responseEntity = controller.create(request);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1L, responseEntity.getBody().id());
        assertEquals("Teclado Mecânico", responseEntity.getBody().name());

        verify(service).createProduct(request);
    }

    @Test
    void shouldFindProductById() {
        // Arrange
        ProductResponseDTO expectedResponse = new ProductResponseDTO(
                1L, "Mouse Gamer", "8000 DPI", new BigDecimal("150.00"), 5
        );
        when(service.getProductById(1L)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ProductResponseDTO> responseEntity = controller.getById(1L);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Mouse Gamer", responseEntity.getBody().name());
        verify(service).getProductById(1L);
    }

    @Test
    void shouldListAllProducts() {
        // Arrange
        ProductResponseDTO expectedResponse = new ProductResponseDTO(
                1L, "Mouse Gamer", "8000 DPI", new BigDecimal("150.00"), 5
        );
        when(service.getAllProducts()).thenReturn(List.of(expectedResponse));

        // Act
        ResponseEntity<List<ProductResponseDTO>> responseEntity = controller.getAll();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isEmpty());
        verify(service).getAllProducts();
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        // Arrange
        ProductRequestDTO request = new ProductRequestDTO(
                "Mouse Pro", "16000 DPI", new BigDecimal("250.00"), 8
        );
        ProductResponseDTO expectedResponse = new ProductResponseDTO(
                1L, "Mouse Pro", "16000 DPI", new BigDecimal("250.00"), 8
        );
        when(service.updateProduct(eq(1L), any(ProductRequestDTO.class))).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ProductResponseDTO> responseEntity = controller.update(1L, request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Mouse Pro", responseEntity.getBody().name());
        verify(service).updateProduct(1L, request);
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        // Arrange
        doNothing().when(service).deleteProduct(1L);

        // Act
        ResponseEntity<Void> responseEntity = controller.delete(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(service).deleteProduct(1L);
    }
}