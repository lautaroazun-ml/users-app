package com.lazun.usersapp.controller;

import com.lazun.usersapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
  private final UserService service;

  @GetMapping("/exists")
  public ResponseEntity<Boolean> existUserByName(@RequestParam(name = "name") String name) {
    Boolean existsUserName = service.existsByName(name);
    if (existsUserName) {
      return ResponseEntity.ok(true);
    }
    return ResponseEntity.notFound().build();
  }
}
