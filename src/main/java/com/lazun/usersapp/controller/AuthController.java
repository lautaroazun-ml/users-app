package com.lazun.usersapp.controller;

import com.lazun.usersapp.models.dto.LoginDTO;
import com.lazun.usersapp.security.JWTAuthResponseDTO;
import com.lazun.usersapp.security.JwtTokenProvider;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @PostMapping("/login")
  public ResponseEntity<JWTAuthResponseDTO> authenticateUser(
      @Valid @RequestBody LoginDTO loginDTO) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getName(), loginDTO.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = jwtTokenProvider.buildToken(authentication);

    return ResponseEntity.ok(new JWTAuthResponseDTO(token));
  }
}
