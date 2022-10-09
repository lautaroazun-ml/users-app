package com.lazun.usersapp.service;

import com.lazun.usersapp.models.entity.User;

public interface UserService {
  Boolean existsByName(String name);

  Boolean existsByEmail(String email);

  User save(User user);
}
