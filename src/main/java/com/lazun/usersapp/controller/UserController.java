package com.lazun.usersapp.controller;

import com.lazun.usersapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
  @Autowired private UserService service;

  @GetMapping("")
  public ResponseEntity<Boolean> existUserByName(@RequestParam(name = "name") String name) {
    Boolean existsUserName = service.existsByName(name);
    if (existsUserName) {
      return ResponseEntity.ok(true);
    }
    return ResponseEntity.notFound().build();
  }
}
