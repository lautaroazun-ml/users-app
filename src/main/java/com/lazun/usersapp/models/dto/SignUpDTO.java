package com.lazun.usersapp.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpDTO {

  @NotBlank(message = "Name may not be blank")
  private String name;

  @NotBlank(message = "Password may not be blank")
  private String password;

  @NotBlank(message = "Email may not be blank")
  @Email
  private String email;
}
