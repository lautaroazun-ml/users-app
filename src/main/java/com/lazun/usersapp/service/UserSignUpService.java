package com.lazun.usersapp.service;

import com.lazun.usersapp.exception.AlreadyExistException;
import com.lazun.usersapp.models.dto.SignUpDTO;

public interface UserSignUpService {

  void signUp(SignUpDTO userDto) throws AlreadyExistException;
}
