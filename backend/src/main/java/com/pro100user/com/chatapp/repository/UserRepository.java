package com.pro100user.com.chatapp.repository;

import com.pro100user.com.chatapp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);
}
