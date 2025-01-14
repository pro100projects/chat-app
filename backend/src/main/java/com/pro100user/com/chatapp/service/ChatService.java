package com.pro100user.com.chatapp.service;

import com.pro100user.com.chatapp.model.entity.ChatMessageEntity;
import com.pro100user.com.chatapp.model.entity.UserEntity;

public interface ChatService {

    ChatMessageEntity saveMessage(UserEntity userEntity, String message);
}
