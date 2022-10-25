package com.lazun.usersapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class UsersAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(UsersAppApplication.class, args);
  }
}
