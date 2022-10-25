package com.lazun.usersapp.rest.user_fee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFeeResponse {

  @JsonProperty("fee_rate")
  private Float feeRate;
}
