package com.orbit_shop.customer.mapper;

import com.orbit_shop.customer.domain.Customer;
import com.orbit_shop.customer.dto.CustomerRequestDTO;
import com.orbit_shop.customer.dto.CustomerResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    // Transforma Entidade em DTO de Saída (Response)
    public CustomerResponseDTO toResponseDTO(Customer customer) {
        if (customer == null) return null;

        // Records não possuem setters, passamos os dados via construtor
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }

    // Transforma DTO de Entrada (Request) em Entidade
    public Customer toEntity(CustomerRequestDTO dto) {
        if (dto == null) return null;

        Customer customer = new Customer();

        // Usamos os getters do Record (que não tem o prefixo "get")
        // e os setters da Entity (gerados pelo Lombok)
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setCpf(dto.cpf());
        customer.setPhone(dto.phone());
        customer.setPassword(dto.password());

        return customer;
    }
}