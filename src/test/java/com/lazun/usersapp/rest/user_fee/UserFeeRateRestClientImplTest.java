package com.lazun.usersapp.rest.user_fee;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.lazun.usersapp.UsersAppApplication;
import com.lazun.usersapp.exception.ApiException;
import com.lazun.usersapp.rest.user_fee.dto.UserFeeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@EnableRetry
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserFeeRateRestClientImpl.class})
@ContextConfiguration(classes = UsersAppApplication.class)
class UserFeeRateRestClientImplTest {

  @SpyBean private UserFeeRateRestClientImpl userFeeRateRestClient;

  @MockBean private RestTemplate restTemplate;

  private static final String defaultURL =
      "https://mock-users-app-lauti.free.beeceptor.com/user_fee_rate";

  private static final String mockErrorURL =
      "https://mock-users-app-lauti.free.beeceptor.com/user_fee_rate_error";

  @BeforeEach
  public void setup() {
    ReflectionTestUtils.setField(userFeeRateRestClient, "defaultURL", defaultURL);
    ReflectionTestUtils.setField(userFeeRateRestClient, "mockErrorURL", mockErrorURL);
  }

  @Test
  public void testGetUserFee_whenAPIResponsesOK_shouldReturnAValue() {
    // Given
    Float feeExpected = 0.1f;
    UserFeeResponse userFeeResponse = new UserFeeResponse();
    userFeeResponse.setFeeRate(feeExpected);
    ResponseEntity<UserFeeResponse> response = new ResponseEntity<>(userFeeResponse, HttpStatus.OK);

    // When
    Mockito.when(restTemplate.exchange(defaultURL, HttpMethod.GET, null, UserFeeResponse.class))
        .thenReturn(response);
    Float feeResult = userFeeRateRestClient.getUserFee(false);

    // Then
    Assertions.assertEquals(feeExpected, feeResult);
  }

  @Test
  public void
      testGetUserFee_GivenMockingErrorTrue_whenAPIResponsesAnError_shouldReturnLastReturned() {
    // Given
    Float feeExpected = 0.1f;
    var exceptionExpected = new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
    // When
    Mockito.when(restTemplate.exchange(mockErrorURL, HttpMethod.GET, null, UserFeeResponse.class))
        .thenThrow(exceptionExpected);
    // Then
    Float feeResult = userFeeRateRestClient.getUserFee(true);

    Assertions.assertEquals(feeExpected, feeResult);
    verify(restTemplate, times(3))
        .exchange(mockErrorURL, HttpMethod.GET, null, UserFeeResponse.class);
  }

  @Test
  public void
      testGetUserFee_GivenMockingErrorTrue_whenAPIResponsesAnError_shouldReturnAPIException() {
    // Given
    var exceptionExpected = new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
    ReflectionTestUtils.setField(userFeeRateRestClient, "lastFeeResponse", null);

    // When
    Mockito.when(restTemplate.exchange(mockErrorURL, HttpMethod.GET, null, UserFeeResponse.class))
        .thenThrow(exceptionExpected);
    // Then
    Assertions.assertThrows(ApiException.class, () -> userFeeRateRestClient.getUserFee(true));
    verify(restTemplate, times(3))
        .exchange(mockErrorURL, HttpMethod.GET, null, UserFeeResponse.class);
  }
}
