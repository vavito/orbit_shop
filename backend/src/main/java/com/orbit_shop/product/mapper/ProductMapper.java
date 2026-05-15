package com.orbit_shop.product.mapper;

import com.orbit_shop.product.domain.Product;
import com.orbit_shop.product.dto.ProductRequestDTO;
import com.orbit_shop.product.dto.ProductResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    /**
     * Converte DTO para Entidade (Criação)
     * Utiliza o método de fábrica estático para garantir que o objeto
     * nasça em um estado válido.
     */
    public Product toEntity(ProductRequestDTO dto) {
        if (dto == null) return null;

        return Product.create(
                dto.name(),
                dto.description(),
                dto.price(),
                dto.quantity()
        );
    }

    public ProductResponseDTO toResponse(Product product) {
        if (product == null) return null;

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity()
        );
    }

    public void applyChanges(Product product, ProductRequestDTO dto) {
        if (product == null || dto == null) return;

        product.changeName(dto.name());
        product.changeDescription(dto.description());
        product.changePrice(dto.price());
        product.redefineStock(dto.quantity());
    }
}