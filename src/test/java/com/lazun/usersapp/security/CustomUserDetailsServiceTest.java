package com.lazun.usersapp.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.lazun.usersapp.models.entity.UserEntity;
import com.lazun.usersapp.repository.UserRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

  @InjectMocks private CustomUserDetailsService service;

  @Mock private UserRepository repository;

  @Test
  public void testLoadUserByUsername_whenUserNotFound_shouldReturnUsernameNotFoundException() {
    // Given
    String usernameOrEmail = "test";

    // When
    given(repository.findByNameOrEmail(usernameOrEmail, usernameOrEmail))
        .willReturn(Optional.empty());

    // Then
    UsernameNotFoundException exception =
        Assertions.assertThrows(
            UsernameNotFoundException.class, () -> service.loadUserByUsername(usernameOrEmail));

    assertEquals("User not found", exception.getMessage());
  }

  @Test
  public void testLoadUserByUsername_whenUserIsFounded_shouldReturnUserDetails() {
    // Given
    String email = "test@test.com";
    UserEntity gotUser = new UserEntity();
    gotUser.setEmail(email);
    gotUser.setPassword("pass");
    gotUser.setRoles(new ArrayList<>());

    // When
    given(repository.findByNameOrEmail(email, email)).willReturn(Optional.of(gotUser));
    UserDetails userDetails = service.loadUserByUsername(email);
    // Then
    assertEquals(gotUser.getEmail(), userDetails.getUsername());
  }
}
