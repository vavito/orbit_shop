package com.orbit_shop.product.mapper;

import com.orbit_shop.product.domain.Product;
import com.orbit_shop.product.dto.ProductRequestDTO;
import com.orbit_shop.product.dto.ProductResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    // DTO -> Entity (criação segura via domínio)
    public Product toEntity(ProductRequestDTO dto) {
        if (dto == null) return null;

        return Product.create(
                dto.name(),
                dto.description(),
                dto.price(),
                dto.quantity()
        );
    }

    // Entity -> Response DTO
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

    // Atualização controlada (sem quebrar o domínio)
    public void updateEntity(Product product, ProductRequestDTO dto) {
        if (product == null || dto == null) return;

        // aqui você usa COMPORTAMENTOS do domínio
        product.updatePrice(dto.price());

        product.updateDescription(dto.description());

        // se quiser, pode validar nome também
        product.updateName(dto.name());

        // quantidade
        product.increaseStock(dto.quantity());
    }
}