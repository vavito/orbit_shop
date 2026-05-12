package com.orbit_shop.customer.mapper;

import com.orbit_shop.customer.domain.Customer;
import com.orbit_shop.customer.dto.CustomerRequestDTO;
import com.orbit_shop.customer.dto.CustomerResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponseDTO toResponseDTO(Customer customer) {
        if (customer == null) return null;

        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }

    public Customer toEntity(CustomerRequestDTO dto) {
        if (dto == null) return null;

        Customer customer = new Customer();

        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setCpf(dto.cpf());
        customer.setPhone(dto.phone());
        customer.setPassword(dto.password());

        return customer;
    }

    public void updateEntityFromDTO(Customer customer, CustomerRequestDTO dto) {
        if (customer == null || dto == null) return;

        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setCpf(dto.cpf());
        customer.setPhone(dto.phone());
        customer.setPassword(dto.password());
    }
}