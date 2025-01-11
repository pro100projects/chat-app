package com.pro100user.com.chatapp.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank
        String name,
        @NotBlank
        String surname,
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        String repeatPassword
) {
}
