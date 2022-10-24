package com.lazun.usersapp.service;

import com.lazun.usersapp.models.entity.UserEntity;

public interface UserService {
  Boolean existsByName(String name);

  Boolean existsByEmail(String email);

  UserEntity save(UserEntity user);
}
