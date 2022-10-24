package com.lazun.usersapp.controller;

import com.lazun.usersapp.models.dto.SignUpDTO;
import com.lazun.usersapp.service.UserSignUpService;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/signup")
public class SignUpController {
  private static final Logger logger = LogManager.getLogger(SignUpController.class);

  @Autowired private UserSignUpService service;

  @PostMapping("")
  public ResponseEntity<String> signUp(@Valid @RequestBody SignUpDTO signUpDTO) {
    service.signUp(signUpDTO);
    return logger.traceExit(
        ResponseEntity.status(HttpStatus.CREATED).body("User created successfully"));
  }
}
