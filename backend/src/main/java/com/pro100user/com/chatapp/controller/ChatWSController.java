package com.pro100user.com.chatapp.controller;

import com.pro100user.com.chatapp.interactor.ChatInteractor;
import com.pro100user.com.chatapp.model.dto.request.ChatMessageRequest;
import com.pro100user.com.chatapp.model.dto.response.ChatMessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatWSController {

    private final ChatInteractor chatInteractor;

    @MessageMapping("/send")
    @SendTo("/chat/topic/messages")
    public ChatMessageResponse handleMessage(@Valid ChatMessageRequest request, Principal principal) {
        return chatInteractor.handleMessage(principal.getName(), request);
    }
}
