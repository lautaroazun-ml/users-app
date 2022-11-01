package com.lazun.usersapp.rest.user_fee;

import com.lazun.usersapp.exception.ApiException;
import com.lazun.usersapp.rest.user_fee.dto.UserFeeResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserFeeRateRestClientImpl implements UserFeeRateRestClient {

  private static final Logger logger = LoggerFactory.getLogger(UserFeeRateRestClientImpl.class);

  @Value("${http.fee.url}")
  private String defaultURL;

  @Value("${http.fee.urlError}")
  private String mockErrorURL;

  private final RestTemplate restTemplate;

  private Float lastFeeResponse;

  @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 50))
  @Cacheable(value = "fee")
  public Float getUserFee(boolean mockError) {
    String url = defaultURL;
    if (mockError) {
      url = mockErrorURL;
    }
    try {
      ResponseEntity<UserFeeResponse> responseEntity =
          restTemplate.exchange(url, HttpMethod.GET, null, UserFeeResponse.class);
      UserFeeResponse userFeeResponse = responseEntity.getBody();
      lastFeeResponse = userFeeResponse.getFeeRate();
      return lastFeeResponse;
    } catch (HttpStatusCodeException exception) {
      logger.info(
          String.format(
              "callToUserFeeRateRestClient Error : %s", exception.getResponseBodyAsString()));
      throw new ApiException(
          HttpStatus.INTERNAL_SERVER_ERROR.name(),
          "An error occurred",
          HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
  }

  /**/
  @Recover
  public Float fallBackUserFee() {
    if (lastFeeResponse == null) {
      throw new ApiException(
          HttpStatus.INTERNAL_SERVER_ERROR.name(),
          "An error occurred",
          HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    return lastFeeResponse;
  }
}
