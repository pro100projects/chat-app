package com.pro100user.com.chatapp.repository;

import com.pro100user.com.chatapp.model.entity.ChatMessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {

    Page<ChatMessageEntity> findAll(Pageable pageable);
}
