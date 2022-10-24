package com.lazun.usersapp.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpDTO {
  @NotNull @NotEmpty private String name;

  @NotNull @NotEmpty private String password;

  @NotNull @Email private String email;
}
