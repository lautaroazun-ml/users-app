package com.lazun.usersapp.rest.user_fee;

import com.lazun.usersapp.exception.ApiException;
import com.lazun.usersapp.rest.user_fee.dto.UserFeeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserFeeRateRestClientImpl implements UserFeeRateRestClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserFeeRateRestClientImpl.class);

  @Value("${http.fee.url}")
  private String DEFAULT_URL;

  @Value("${http.fee.urlError}")
  private String MOCK_ERROR_URL;

  @Autowired private RestTemplate restTemplate;

  private Float lastFeeResponse;

  @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 50))
  @Cacheable(value = "fee")
  public Float getUserFee(boolean mockError) {
    String url = DEFAULT_URL;
    if (mockError) {
      url = MOCK_ERROR_URL;
    }
    UserFeeResponse response = restTemplate.getForObject(url, UserFeeResponse.class);
    lastFeeResponse = response.getFeeRate();
    return lastFeeResponse;
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
