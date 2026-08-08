    package com.orbit_shop.product.service;


    import com.orbit_shop.customer.domain.Customer;
    import com.orbit_shop.product.domain.Product;
    import com.orbit_shop.product.dto.ProductRequestDTO;
    import com.orbit_shop.product.dto.ProductResponseDTO;
    import com.orbit_shop.product.mapper.ProductMapper;
    import com.orbit_shop.product.repository.ProductRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class ProductService {

        private final ProductRepository repository;
        private final ProductMapper mapper;

        public ProductResponseDTO createProduct(ProductRequestDTO dto) {

            Product product = mapper.toEntity(dto);

            Product saved = repository.save(product);

            return mapper.toResponse(saved);
        }

        public ProductResponseDTO getProductById(Long id) {

            Product product = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            return mapper.toResponse(product);
        }

        public List<ProductResponseDTO> getAllProducts() {

            return repository.findAll()
                    .stream()
                    .map(mapper::toResponse)
                    .toList();
        }

        public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {

            Product product = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            mapper.applyChanges(product, dto);

            Product updated = repository.save(product);

            return mapper.toResponse(updated);
        }

        public void deleteProduct(Long id) {

            if (!repository.existsById(id)) {
                throw new RuntimeException("Produto não encontrado");
            }

            repository.deleteById(id);
        }

        public Product findById(Long id) {
            return repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
        }
    }