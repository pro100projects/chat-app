package com.pro100user.com.chatapp.service.impl;

import com.pro100user.com.chatapp.model.entity.ChatMessageEntity;
import com.pro100user.com.chatapp.model.entity.UserEntity;
import com.pro100user.com.chatapp.repository.ChatMessageRepository;
import com.pro100user.com.chatapp.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    @Transactional
    public ChatMessageEntity saveMessage(UserEntity userEntity, String message) {
        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        chatMessageEntity.setUser(userEntity);
        chatMessageEntity.setMessage(message);
        return chatMessageRepository.saveAndFlush(chatMessageEntity);
    }
}
