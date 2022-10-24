package com.lazun.usersapp.models.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {
  @NotNull @NotEmpty private String name;

  @NotNull @NotEmpty private String password;
}
