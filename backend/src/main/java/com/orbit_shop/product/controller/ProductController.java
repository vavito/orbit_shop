package com.orbit_shop.product.controller;

import com.orbit_shop.product.dto.ProductRequestDTO;
import com.orbit_shop.product.dto.ProductResponseDTO;
import com.orbit_shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ProductResponseDTO create(@Valid @RequestBody ProductRequestDTO dto) {
        return service.createProduct(dto);
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @GetMapping
    public List<ProductResponseDTO> getAll() {
        return service.getAllProducts();
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable Long id,
                                     @Valid @RequestBody ProductRequestDTO dto) {
        return service.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteProduct(id);
    }
}