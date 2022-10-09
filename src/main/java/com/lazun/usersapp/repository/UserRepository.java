package com.lazun.usersapp.repository;

import com.lazun.usersapp.models.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByName(String username);

  Boolean existsByName(String username);

  Boolean existsByEmail(String email);
}
