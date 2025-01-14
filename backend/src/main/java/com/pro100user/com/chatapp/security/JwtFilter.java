package com.pro100user.com.chatapp.security;

import com.pro100user.com.chatapp.configuration.config.SecurityConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final SecurityConfig securityConfig;
    private final UserSecurityService userSecurityService;

    private static final String AUTHORIZATION_PARAMETER = "authorization";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final var token = getTokenFromRequest(request);
            if (token == null) {
                throw new IllegalArgumentException("JWT token is missing");
            }

            final var email = getSubjectFromToken(token);
            final var userSecurity = userSecurityService.loadUserByUsername(email);
            final var authentication = new UsernamePasswordAuthenticationToken(userSecurity, null, userSecurity.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
        }
    }

    public String getTokenFromRequest(final HttpServletRequest request) {
        String bearerToken = request.getParameter(AUTHORIZATION_PARAMETER);
        if (bearerToken == null) {
            bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        }

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getSubjectFromToken(final String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(securityConfig.jwt().secret().getBytes()).build()
                    .parseClaimsJws(token).getBody();
            if (claims.getExpiration().before(new Date())) {
                throw new IllegalArgumentException("Jwt token is expired");
            }
            return claims.getSubject();
        } catch (final Exception e) {
            throw new IllegalArgumentException("Jwt token is not valid");
        }
    }
}
