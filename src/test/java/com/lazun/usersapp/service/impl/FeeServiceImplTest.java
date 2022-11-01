package com.lazun.usersapp.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.lazun.usersapp.models.dto.AmountDTO;
import com.lazun.usersapp.rest.user_fee.UserFeeRateRestClient;
import com.lazun.usersapp.rest.user_fee.UserFeeRateRestClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FeeServiceImplTest {

  private FeeServiceImpl feeService;

  private UserFeeRateRestClient userFeeRateRestClient;

  @BeforeEach
  public void setup() {

    userFeeRateRestClient = mock(UserFeeRateRestClientImpl.class);

    feeService = new FeeServiceImpl(userFeeRateRestClient);
  }

  @Test
  public void testSumWithFee_whenAPIResponsesOK_shouldReturnANewAmount() {
    // Given
    Integer firstNumber = 1;
    Integer sndNumber = 2;
    float fee = 0.1f;
    Float sumExpected = (firstNumber + sndNumber) * (1 + fee);
    given(userFeeRateRestClient.getUserFee(false)).willReturn(fee);
    // When
    AmountDTO newAmount = feeService.sumWithFee(firstNumber, sndNumber, false);
    // Then
    assertEquals(sumExpected, newAmount.getAmount());
  }
}
