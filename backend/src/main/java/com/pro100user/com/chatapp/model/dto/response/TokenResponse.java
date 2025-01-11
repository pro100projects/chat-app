package com.pro100user.com.chatapp.model.dto.response;

public record TokenResponse(
        String authToken,
        String refreshToken
) {
}
