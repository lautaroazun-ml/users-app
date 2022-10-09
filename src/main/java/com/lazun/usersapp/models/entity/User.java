package com.lazun.usersapp.models.entity;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank private String name;

  @Column(unique = true)
  private String email;

  private String password;

  @Column(name = "created_date")
  private LocalDateTime createdDate = LocalDateTime.now();

  @Column(name = "updated_date")
  private LocalDateTime updateDate = LocalDateTime.now();
}
