package com.lazun.usersapp.service.impl;

import com.lazun.usersapp.exception.AlreadyExistException;
import com.lazun.usersapp.models.dto.SignUpDTO;
import com.lazun.usersapp.models.entity.UserEntity;
import com.lazun.usersapp.service.UserService;
import com.lazun.usersapp.service.UserSignUpService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSignUpServiceImpl implements UserSignUpService {

  private final PasswordEncoder passwordEncoder;

  private final UserService userService;

  @Transactional
  public void signUp(SignUpDTO userDto) throws AlreadyExistException {
    if (userService.existsByEmail(userDto.getEmail())) {
      throw new AlreadyExistException(
          "There is an account with that email address: " + userDto.getEmail());
    }
    UserEntity user = new UserEntity();
    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user.setCreatedDate(LocalDateTime.now());

    userService.save(user);
  }
}
