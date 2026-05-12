package com.orbit_shop.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerRequestDTO(
        @NotBlank String name,
        @Email @NotBlank String email,
        @CPF @NotBlank String cpf,
        @NotBlank
        @Pattern
                (regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$",
                message = "Formato de telefone inválido (use (XX) XXXXX-XXXX)")
        String phone,
        @NotBlank @Size(min = 8) String password
        ) {
}
