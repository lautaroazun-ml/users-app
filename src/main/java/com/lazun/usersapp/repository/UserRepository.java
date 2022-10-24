package com.lazun.usersapp.repository;

import com.lazun.usersapp.models.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByName(String username);

  Boolean existsByName(String username);

  Boolean existsByEmail(String email);

  Optional<UserEntity> findByNameOrEmail(String username, String email);
}
