package com.lazun.usersapp.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.lazun.usersapp.models.entity.UserEntity;
import com.lazun.usersapp.repository.UserRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @InjectMocks private UserServiceImpl userService;

  @Mock private UserRepository repository;

  @Test
  public void testExistsByName_whenRepositoryResponsesOK_shouldReturnAValue() {
    // Given
    String name = "test";
    Boolean resultExpected = false;
    // When
    given(repository.existsByName(name)).willReturn(resultExpected);
    Boolean result = userService.existsByName(name);
    // Then
    assertEquals(resultExpected, result);
  }

  @Test
  public void testExistsByEmail_whenRepositoryResponsesOK_shouldReturnAValue() {
    // Given
    String email = "test@test.com";
    Boolean resultExpected = false;
    // When
    given(repository.existsByEmail(email)).willReturn(resultExpected);
    Boolean result = userService.existsByEmail(email);
    // Then
    assertEquals(resultExpected, result);
  }

  @Test
  public void testSave_whenRepositoryResponsesOK_shouldReturnAnUserEntity() {
    // Given
    UserEntity user = new UserEntity();
    user.setName("test");
    user.setPassword("pass");
    user.setEmail("test@test.com");
    user.setCreatedDate(LocalDateTime.now());
    // When
    given(repository.save(user)).willReturn(user);
    UserEntity savedUser = userService.save(user);
    // Then
    assertEquals(user, savedUser);
  }
}
