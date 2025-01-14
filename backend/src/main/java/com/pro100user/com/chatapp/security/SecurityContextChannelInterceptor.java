package com.pro100user.com.chatapp.security;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (accessor.getSessionAttributes() != null) {
            Authentication authentication =
                (Authentication) accessor.getSessionAttributes().get("SPRING_SECURITY_CONTEXT");

            if (authentication != null) {
                accessor.setUser(authentication);
            }
        }

        return message;
    }
}
