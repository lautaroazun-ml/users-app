package com.lazun.usersapp.service.impl;

import com.lazun.usersapp.models.entity.User;
import com.lazun.usersapp.repository.UserRepository;
import com.lazun.usersapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository repository;

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
  public User save(User user) {
    return repository.save(user);
  }
}
