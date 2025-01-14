package com.pro100user.com.chatapp.service;

import com.pro100user.com.chatapp.model.entity.ChatMessageEntity;
import com.pro100user.com.chatapp.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatService {

    Page<ChatMessageEntity> findAll(Pageable pageable);

    ChatMessageEntity saveMessage(UserEntity userEntity, String message);
}
