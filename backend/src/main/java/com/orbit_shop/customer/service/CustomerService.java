package com.orbit_shop.customer.service;

import com.orbit_shop.customer.domain.Customer;
import com.orbit_shop.customer.dto.CustomerRequestDTO;
import com.orbit_shop.customer.dto.CustomerResponseDTO;
import com.orbit_shop.customer.mapper.CustomerMapper;
import com.orbit_shop.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerResponseDTO create(CustomerRequestDTO dto) {
        Customer customer = mapper.toEntity(dto);
        Customer savedCustomer = repository.save(customer);

        return mapper.toResponseDTO(savedCustomer);
    }

    public CustomerResponseDTO find(Long id) {
        Customer customer = findById(id);
        return mapper.toResponseDTO(customer);
    }

    public List<CustomerResponseDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public CustomerResponseDTO update(Long id, CustomerRequestDTO dto) {
        Customer customer = findById(id);

        mapper.updateEntityFromDTO(customer, dto);

        Customer updatedCustomer = repository.save(customer);

        return mapper.toResponseDTO(updatedCustomer);
    }

    public void delete(Long id) {
        Customer customer = findById(id);
        repository.delete(customer);
    }

    public Customer findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
    }
}