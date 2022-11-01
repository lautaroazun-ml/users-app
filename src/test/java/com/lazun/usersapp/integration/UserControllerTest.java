package com.lazun.usersapp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.lazun.usersapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserControllerTest extends ControllerTest {

  @MockBean private UserService userService;

  @Test
  void getExistUserUnauthorized() {
    given(userService.existsByName("test")).willReturn(true);

    ResponseEntity<Boolean> responseEntity =
        this.testRestTemplate.exchange(
            "/users/exists/test", HttpMethod.GET, this.getDefaultRequestEntity(), Boolean.class);

    assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
  }
}
