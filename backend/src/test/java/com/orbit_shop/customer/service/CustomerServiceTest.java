package com.orbit_shop.customer.service;

import com.orbit_shop.customer.domain.Customer;
import com.orbit_shop.customer.dto.CustomerRequestDTO;
import com.orbit_shop.customer.dto.CustomerResponseDTO;
import com.orbit_shop.customer.mapper.CustomerMapper;
import com.orbit_shop.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerService service;

    @Test
    void shouldCreateCustomerSuccessfully() {
        CustomerRequestDTO dto = new CustomerRequestDTO(
                "Jonas",
                "jonas@gmail.com",
                "52998224725",
                "(62) 98888-8888",
                "jonas12345"
        );

        Customer customer = Customer.create(
                dto.name(),
                dto.email(),
                dto.cpf(),
                dto.phone(),
                dto.password()
        );

        Customer savedCustomer = Customer.create(
                dto.name(),
                dto.email(),
                dto.cpf(),
                dto.phone(),
                dto.password()
        );

        ReflectionTestUtils.setField(savedCustomer, "id", 1L);

        CustomerResponseDTO response = new CustomerResponseDTO(
                1L,
                "Jonas",
                "jonas@gmail.com",
                "(62) 98888-8888"
        );

        when(mapper.toEntity(dto)).thenReturn(customer);
        when(repository.save(customer)).thenReturn(savedCustomer);
        when(mapper.toResponseDTO(savedCustomer)).thenReturn(response);

        CustomerResponseDTO result = service.create(dto);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Jonas", result.name());

        verify(mapper).toEntity(dto);
        verify(repository).save(customer);
        verify(mapper).toResponseDTO(savedCustomer);
    }

    @Test
    void shouldFindCustomerById() {
        Customer customer = Customer.create(
                "Jonas",
                "jonas@gmail.com",
                "52998224725",
                "(62) 98888-8888",
                "jonas12345"
        );

        ReflectionTestUtils.setField(customer, "id", 1L);

        CustomerResponseDTO response = new CustomerResponseDTO(
                1L,
                "Jonas",
                "jonas@gmail.com",
                "(62) 98888-8888"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(customer));
        when(mapper.toResponseDTO(customer)).thenReturn(response);

        CustomerResponseDTO result = service.find(1L);

        assertEquals(1L, result.id());
        assertEquals("Jonas", result.name());

        verify(repository).findById(1L);
        verify(mapper).toResponseDTO(customer);
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.find(99L)
        );

        assertEquals("Cliente não encontrado!", exception.getMessage());

        verify(repository).findById(99L);
    }

    @Test
    void shouldListAllCustomers() {
        Customer customer = Customer.create(
                "Jonas",
                "jonas@gmail.com",
                "52998224725",
                "(62) 98888-8888",
                "jonas12345"
        );

        CustomerResponseDTO response = new CustomerResponseDTO(
                1L,
                "Jonas",
                "jonas@gmail.com",
                "(62) 98888-8888"
        );

        when(repository.findAll()).thenReturn(List.of(customer));
        when(mapper.toResponseDTO(customer)).thenReturn(response);

        List<CustomerResponseDTO> result = service.listAll();

        assertEquals(1, result.size());
        assertEquals("Jonas", result.get(0).name());

        verify(repository).findAll();
        verify(mapper).toResponseDTO(customer);
    }

    @Test
    void shouldUpdateCustomerSuccessfully() {
        Long id = 1L;

        Customer customer = Customer.create(
                "Jonas",
                "jonas@gmail.com",
                "52998224725",
                "(62) 98888-8888",
                "jonas12345"
        );

        CustomerRequestDTO dto = new CustomerRequestDTO(
                "Maria",
                "maria@gmail.com",
                "12345678901",
                "(11) 97777-7777",
                "maria12345"
        );

        CustomerResponseDTO response = new CustomerResponseDTO(
                1L,
                "Maria",
                "maria@gmail.com",
                "(11) 97777-7777"
        );

        when(repository.findById(id)).thenReturn(Optional.of(customer));
        when(repository.save(customer)).thenReturn(customer);
        when(mapper.toResponseDTO(customer)).thenReturn(response);

        CustomerResponseDTO result = service.update(id, dto);

        assertEquals("Maria", result.name());
        assertEquals("maria@gmail.com", result.email());

        verify(repository).findById(id);
        verify(mapper).updateEntityFromDTO(customer, dto);
        verify(repository).save(customer);
        verify(mapper).toResponseDTO(customer);
    }

    @Test
    void shouldDeleteCustomerSuccessfully() {
        Long id = 1L;

        Customer customer = Customer.create(
                "Jonas",
                "jonas@gmail.com",
                "52998224725",
                "(62) 98888-8888",
                "jonas12345"
        );

        when(repository.findById(id)).thenReturn(Optional.of(customer));

        service.delete(id);

        verify(repository).findById(id);
        verify(repository).delete(customer);
    }
}