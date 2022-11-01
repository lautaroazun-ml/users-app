package com.lazun.usersapp.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.lazun.usersapp.exception.AlreadyExistException;
import com.lazun.usersapp.models.dto.SignUpDTO;
import com.lazun.usersapp.models.entity.UserEntity;
import com.lazun.usersapp.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserSignUpServiceImplTest {

  @InjectMocks private UserSignUpServiceImpl userSignUpService;

  @Mock private UserService userService;

  @Mock private PasswordEncoder passwordEncoder;

  @Test
  public void testSignUP_whenUserExists_shouldReturnAnException() {
    // Given
    SignUpDTO user = new SignUpDTO();
    user.setName("test");
    user.setEmail("test@test.com");
    user.setPassword("testPass");

    // When
    given(userService.existsByEmail(user.getEmail())).willReturn(true);

    // Then
    AlreadyExistException existException =
        Assertions.assertThrows(AlreadyExistException.class, () -> userSignUpService.signUp(user));

    assertTrue(
        existException.getDescription().startsWith("There is an account with that email address:"));
  }

  @Test
  public void testSignUP_whenUserIsOK_shouldReturnNothing() {
    // Given
    String encodedPass = "asd21343asdasd$123.09";
    SignUpDTO user = new SignUpDTO();
    user.setName("test");
    user.setEmail("test@test.com");
    user.setPassword("testPass");

    // When
    given(userService.existsByEmail(user.getEmail())).willReturn(false);
    given(userService.save(any(UserEntity.class))).willReturn(new UserEntity());
    given(passwordEncoder.encode(user.getPassword())).willReturn(encodedPass);
    // Then
    assertDoesNotThrow(() -> userSignUpService.signUp(user));
    verify(userService, times(1)).save(any(UserEntity.class));
  }
}
