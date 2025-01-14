package com.pro100user.com.chatapp.model.dto.response;

import java.time.LocalDateTime;

public record ChatMessageResponse(
        Long id,
        UserResponse user,
        String message,
        LocalDateTime createdAt
) {
}
