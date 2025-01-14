package com.pro100user.com.chatapp.service.impl;

import com.pro100user.com.chatapp.mapper.ChatMapper;
import com.pro100user.com.chatapp.model.entity.ChatMessageEntity;
import com.pro100user.com.chatapp.model.entity.UserEntity;
import com.pro100user.com.chatapp.repository.ChatMessageRepository;
import com.pro100user.com.chatapp.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMapper chatMapper;
    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public Page<ChatMessageEntity> findAll(Pageable pageable) {
        return chatMessageRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public ChatMessageEntity saveMessage(UserEntity userEntity, String message) {
        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        chatMessageEntity.setUser(userEntity);
        chatMessageEntity.setMessage(message);
        return chatMessageRepository.saveAndFlush(chatMessageEntity);
    }

    @Override
    public void createJoiningToChatMessage(UserEntity userEntity) {
        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        // in future we can set some system user
        chatMessageEntity.setUser(userEntity);
        // it can be some flag in entity to mark that it is joining message
        // and in frontend add some handling how to show it
        chatMessageEntity.setMessage(String.format("%s joined the chat", userEntity.getUsername()));
        chatMessageEntity = chatMessageRepository.saveAndFlush(chatMessageEntity);
        simpMessagingTemplate.convertAndSend("/chat/topic/messages",
                chatMapper.toChatMessageResponse(chatMessageEntity));
    }
}
