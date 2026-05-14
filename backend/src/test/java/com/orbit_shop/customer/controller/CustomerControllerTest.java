package com.orbit_shop.customer.controller;

import com.orbit_shop.customer.dto.CustomerResponseDTO;
import com.orbit_shop.customer.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
                "password": "jonas123"
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
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jonas"))
                .andExpect(jsonPath("$.email").value("jonas@gmail.com"))
                .andExpect(jsonPath("$.phone").value("(62) 98888-8888"));

        verify(service).create(any());
    }

    @Test
    void shouldReturnBadRequestWhenCpfIsInvalid() throws Exception {
        String json = """
            {
                "name": "Jonas",
                "email": "jonas@gmail.com",
                "cpf": "0666666661",
                "phone": "(62) 98888-8888",
                "password": "jonas123"
            }
        """;

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
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
                .andDo(print())
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
                "password": "jonas123"
            }
        """;

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(service, never()).create(any());
    }
}