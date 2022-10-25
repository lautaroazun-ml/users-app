package com.lazun.usersapp.models.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
  @NotBlank(message = "Name may not be blank")
  private String name;

  @NotBlank(message = "Password may not be blank")
  private String password;
}
