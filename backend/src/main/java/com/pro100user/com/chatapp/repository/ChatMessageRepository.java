package com.pro100user.com.chatapp.repository;

import com.pro100user.com.chatapp.model.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
}
