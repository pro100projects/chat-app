package com.pro100user.com.chatapp.security;

import com.pro100user.com.chatapp.configuration.config.SecurityConfig;
import com.pro100user.com.chatapp.model.dto.response.TokenResponse;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final SecurityConfig securityConfig;

    public TokenResponse generateTokens(UserPrincipal userPrincipal) {
        Date accessTokenExpirationDate = Date.from(
                LocalDate.now()
                        .plusDays(15)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()
        );

        Date refreshTokenExpirationDate = Date.from(
                LocalDate.now()
                        .plusDays(30)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()
        );

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", userPrincipal.getId());
        claims.put("roles", userPrincipal.getAuthorities());
        claims.put("enabled", userPrincipal.isEnabled());

        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(securityConfig.jwt().secret().getBytes()), SignatureAlgorithm.HS512);

        String accessToken = jwtBuilder.setExpiration(accessTokenExpirationDate).compact();
        String refreshToken = jwtBuilder.setExpiration(refreshTokenExpirationDate).compact();

        return new TokenResponse(accessToken, refreshToken);
    }
}
