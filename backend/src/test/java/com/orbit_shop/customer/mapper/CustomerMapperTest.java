package com.orbit_shop.customer.mapper;

import com.orbit_shop.customer.domain.Customer;
import com.orbit_shop.customer.dto.CustomerRequestDTO;
import com.orbit_shop.customer.dto.CustomerResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    private final CustomerMapper mapper = new CustomerMapper();

    @Test
    void shouldConvertRequestDtoToEntity() {
        CustomerRequestDTO dto = new CustomerRequestDTO(
                "Jonas",
                "jonas@gmail.com",
                "52998224725",
                "(62) 98888-8888",
                "jonas123"
        );

        Customer customer = mapper.toEntity(dto);

        assertNotNull(customer);
        assertEquals("Jonas", customer.getName());
        assertEquals("jonas@gmail.com", customer.getEmail());
        assertEquals("52998224725", customer.getCpf());
        assertEquals("(62) 98888-8888", customer.getPhone());
        assertEquals("jonas123", customer.getPassword());
    }

    @Test
    void shouldConvertEntityToResponseDto() {
        Customer customer = Customer.create(
                "Jonas",
                "jonas@gmail.com",
                "52998224725",
                "(62) 98888-8888",
                "jonas123"
        );

        ReflectionTestUtils.setField(customer, "id", 1L);

        CustomerResponseDTO response = mapper.toResponseDTO(customer);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Jonas", response.name());
        assertEquals("jonas@gmail.com", response.email());
        assertEquals("(62) 98888-8888", response.phone());
    }

    @Test
    void shouldReturnNullWhenRequestDtoIsNull() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void shouldReturnNullWhenCustomerIsNull() {
        assertNull(mapper.toResponseDTO(null));
    }

    @Test
    void shouldUpdateEntityFromDto() {
        Customer customer = Customer.create(
                "Jonas",
                "jonas@gmail.com",
                "52998224725",
                "(62) 98888-8888",
                "jonas123"
        );

        CustomerRequestDTO dto = new CustomerRequestDTO(
                "Maria",
                "maria@gmail.com",
                "12345678901",
                "(11) 97777-7777",
                "maria123"
        );

        mapper.updateEntityFromDTO(customer, dto);

        assertEquals("Maria", customer.getName());
        assertEquals("maria@gmail.com", customer.getEmail());
        assertEquals("12345678901", customer.getCpf());
        assertEquals("(11) 97777-7777", customer.getPhone());
        assertEquals("maria123", customer.getPassword());
    }
}