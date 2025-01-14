package com.pro100user.com.chatapp.interactor.impl;

import com.pro100user.com.chatapp.interactor.ChatInteractor;
import com.pro100user.com.chatapp.mapper.ChatMapper;
import com.pro100user.com.chatapp.model.dto.request.ChatMessageRequest;
import com.pro100user.com.chatapp.model.dto.response.ChatMessageResponse;
import com.pro100user.com.chatapp.service.ChatService;
import com.pro100user.com.chatapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatInteractorImpl implements ChatInteractor {

    private final ChatMapper chatMapper;
    private final ChatService chatService;
    private final UserService userService;

    @Override
    public ChatMessageResponse handleMessage(String username, ChatMessageRequest request) {
        var userEntity = userService.findByUsername(username).orElseThrow();
        var chatMessageEntity = chatService.saveMessage(userEntity, request.message());
        return chatMapper.toChatMessageResponse(chatMessageEntity);
    }
}
