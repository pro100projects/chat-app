package com.pro100user.com.chatapp.configuration.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security")
public record SecurityConfig(
        Jwt jwt
) {

    public record Jwt(String secret) {
    }
}
