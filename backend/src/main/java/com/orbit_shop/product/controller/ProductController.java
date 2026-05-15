package com.orbit_shop.product.controller;

import com.orbit_shop.product.dto.ProductRequestDTO;
import com.orbit_shop.product.dto.ProductResponseDTO;
import com.orbit_shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO response = service.createProduct(dto);
        // Retorna 201 Created: O status correto para criação de novos recursos
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        ProductResponseDTO response = service.getProductById(id);
        // Retorna 200 OK: Padrão para buscas bem-sucedidas
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        List<ProductResponseDTO> response = service.getAllProducts();
        // Retorna 200 OK: Padrão para listagem
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO dto
    ) {
        ProductResponseDTO response = service.updateProduct(id, dto);
        // Retorna 200 OK: Indica que o recurso foi atualizado com sucesso e o corpo contém a nova versão
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteProduct(id);
        // Retorna 204 No Content: O status ideal para deleção, indicando sucesso sem corpo de resposta
        return ResponseEntity.noContent().build();
    }
}