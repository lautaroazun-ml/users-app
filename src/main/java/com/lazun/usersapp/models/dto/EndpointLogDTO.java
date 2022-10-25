package com.lazun.usersapp.models.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EndpointLogDTO {

  private String requestURL;

  private String requestBody;

  private Integer responseStatus;

  private String responseBody;

  private LocalDateTime createdDate = LocalDateTime.now();
}
