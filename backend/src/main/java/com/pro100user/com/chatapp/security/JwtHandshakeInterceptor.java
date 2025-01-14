package com.pro100user.com.chatapp.security;

import com.pro100user.com.chatapp.configuration.config.SecurityConfig;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtFilter jwtFilter;
    private final SecurityConfig securityConfig;
    private final UserSecurityService userSecurityService;

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes
    ) {
        try {
            if (request instanceof HttpServletRequest httpServletRequest) {
                String token = jwtFilter.getTokenFromRequest(httpServletRequest);

                if (token == null) {
                    throw new IllegalArgumentException("JWT token is missing");
                }

                String email = jwtFilter.getSubjectFromToken(token);
                var userSecurity = userSecurityService.loadUserByUsername(email);
                var authentication = new UsernamePasswordAuthenticationToken(
                        userSecurity, null, userSecurity.getAuthorities());

                attributes.put("SPRING_SECURITY_CONTEXT", authentication);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception
    ) {}
}
