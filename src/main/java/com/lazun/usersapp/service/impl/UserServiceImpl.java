package com.lazun.usersapp.service.impl;

import com.lazun.usersapp.models.entity.UserEntity;
import com.lazun.usersapp.repository.UserRepository;
import com.lazun.usersapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

  @Override
  @Transactional(readOnly = true)
  public Boolean existsByName(String name) {
    return repository.existsByName(name);
  }

  @Override
  @Transactional(readOnly = true)
  public Boolean existsByEmail(String email) {
    return repository.existsByEmail(email);
  }

  @Override
  @Transactional
  public UserEntity save(UserEntity user) {
    return repository.save(user);
  }
}
