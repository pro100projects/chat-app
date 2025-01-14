package com.pro100user.com.chatapp.controller;

import com.pro100user.com.chatapp.interactor.ChatInteractor;
import com.pro100user.com.chatapp.model.dto.response.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatInteractor chatInteractor;

    @GetMapping("/messages")
    public ResponseEntity<Page<ChatMessageResponse>> getMessages(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size
    ) {
        Page<ChatMessageResponse> messages = chatInteractor.findAll(page, size);
        return ResponseEntity.ok(messages);
    }
}
