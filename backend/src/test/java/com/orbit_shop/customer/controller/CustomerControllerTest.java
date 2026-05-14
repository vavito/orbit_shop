package com.orbit_shop.customer.controller;

import com.orbit_shop.customer.dto.CustomerResponseDTO;
import com.orbit_shop.customer.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService service;

    @Test
    void shouldCreateCustomerSuccessfully() throws Exception {
        String json = """
            {
                "name": "Jonas",
                "email": "jonas@gmail.com",
                "cpf": "52998224725",
                "phone": "(62) 98888-8888",
                "password": "jonas12345"
            }
        """;

        CustomerResponseDTO response = new CustomerResponseDTO(
                1L,
                "Jonas",
                "jonas@gmail.com",
                "(62) 98888-8888"
        );

        when(service.create(any())).thenReturn(response);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jonas"))
                .andExpect(jsonPath("$.email").value("jonas@gmail.com"))
                .andExpect(jsonPath("$.phone").value("(62) 98888-8888"));

        verify(service).create(any());
    }

    @Test
    void shouldFindCustomerById() throws Exception {
        CustomerResponseDTO response = new CustomerResponseDTO(
                1L,
                "Jonas",
                "jonas@gmail.com",
                "(62) 98888-8888"
        );

        when(service.find(1L)).thenReturn(response);

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jonas"));

        verify(service).find(1L);
    }

    @Test
    void shouldListAllCustomers() throws Exception {
        CustomerResponseDTO response = new CustomerResponseDTO(
                1L,
                "Jonas",
                "jonas@gmail.com",
                "(62) 98888-8888"
        );

        when(service.listAll()).thenReturn(List.of(response));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Jonas"));

        verify(service).listAll();
    }

    @Test
    void shouldUpdateCustomerSuccessfully() throws Exception {
        String json = """
            {
                "name": "Maria",
                "email": "maria@gmail.com",
                "cpf": "52998224725",
                "phone": "(11) 97777-7777",
                "password": "maria12345"
            }
        """;

        CustomerResponseDTO response = new CustomerResponseDTO(
                1L,
                "Maria",
                "maria@gmail.com",
                "(11) 97777-7777"
        );

        when(service.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Maria"))
                .andExpect(jsonPath("$.email").value("maria@gmail.com"));

        verify(service).update(eq(1L), any());
    }

    @Test
    void shouldDeleteCustomerSuccessfully() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isNoContent());

        verify(service).delete(1L);
    }

    @Test
    void shouldReturnBadRequestWhenCpfIsInvalid() throws Exception {
        String json = """
            {
                "name": "Jonas",
                "email": "jonas@gmail.com",
                "cpf": "0666666661",
                "phone": "(62) 98888-8888",
                "password": "jonas12345"
            }
        """;

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        verify(service, never()).create(any());
    }

    @Test
    void shouldReturnBadRequestWhenPasswordIsTooShort() throws Exception {
        String json = """
            {
                "name": "Jonas",
                "email": "jonas@gmail.com",
                "cpf": "52998224725",
                "phone": "(62) 98888-8888",
                "password": "123"
            }
        """;

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        verify(service, never()).create(any());
    }

    @Test
    void shouldReturnBadRequestWhenPhoneFormatIsInvalid() throws Exception {
        String json = """
            {
                "name": "Jonas",
                "email": "jonas@gmail.com",
                "cpf": "52998224725",
                "phone": "62988888888",
                "password": "jonas12345"
            }
        """;

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        verify(service, never()).create(any());
    }
}