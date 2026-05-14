package com.orbit_shop.customer.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void shouldCreateCustomerSuccessfully() {
        Customer customer = Customer.create(
                "Jonas",
                "jonas@gmail.com",
                "52998224725",
                "(62) 98888-8888",
                "jonas123"
        );

        assertEquals("Jonas", customer.getName());
        assertEquals("jonas@gmail.com", customer.getEmail());
        assertEquals("52998224725", customer.getCpf());
        assertEquals("(62) 98888-8888", customer.getPhone());
        assertEquals("jonas123", customer.getPassword());
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                Customer.create("", "jonas@gmail.com", "52998224725", "(62) 98888-8888", "jonas123")
        );
    }

    @Test
    void shouldThrowExceptionWhenEmailIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                Customer.create("Jonas", "", "52998224725", "(62) 98888-8888", "jonas123")
        );
    }

    @Test
    void shouldThrowExceptionWhenCpfIsInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                Customer.create("Jonas", "jonas@gmail.com", "123", "(62) 98888-8888", "jonas123")
        );
    }

    @Test
    void shouldUpdateName() {
        Customer customer = Customer.create(
                "Jonas",
                "jonas@gmail.com",
                "52998224725",
                "(62) 98888-8888",
                "jonas123"
        );

        customer.updateName("Maria");

        assertEquals("Maria", customer.getName());
    }

    @Test
    void shouldUpdatePhone() {
        Customer customer = Customer.create(
                "Jonas",
                "jonas@gmail.com",
                "52998224725",
                "(62) 98888-8888",
                "jonas123"
        );

        customer.updatePhone("(11) 97777-7777");

        assertEquals("(11) 97777-7777", customer.getPhone());
    }

    @Test
    void shouldChangePassword() {
        Customer customer = Customer.create(
                "Jonas",
                "jonas@gmail.com",
                "52998224725",
                "(62) 98888-8888",
                "jonas123"
        );

        customer.changePassword("novaSenha123");

        assertEquals("novaSenha123", customer.getPassword());
    }
}