package com.pro100user.com.chatapp.controller;

import com.pro100user.com.chatapp.model.dto.websocket.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {

    @MessageMapping("/send")
    @SendTo("/chat/topic/messages")
    public ChatMessage handleMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());
        return message;
    }
}
