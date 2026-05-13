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

        return Customer.create(
                dto.name(),
                dto.email(),
                dto.cpf(),
                dto.phone(),
                dto.password()
        );
    }

    public void updateEntityFromDTO(Customer customer, CustomerRequestDTO dto) {
        if (customer == null || dto == null) return;

        customer.updateName(dto.name());
        customer.updateEmail(dto.email());
        customer.updateCpf(dto.cpf());
        customer.updatePhone(dto.phone());
        customer.changePassword(dto.password());
    }
}