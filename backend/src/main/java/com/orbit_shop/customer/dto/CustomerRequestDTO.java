package com.orbit_shop.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.aspectj.weaver.ast.Not;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerRequestDTO(
        @NotBlank String name,
        @Email @NotBlank String email,
        @CPF @NotBlank String cpf,
        @NotBlank String phone,
        @NotBlank @Size(min = 8) String password
        ) {
}
