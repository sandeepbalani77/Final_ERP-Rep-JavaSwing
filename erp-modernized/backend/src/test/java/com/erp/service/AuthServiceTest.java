package com.erp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.erp.config.JwtUtil;
import com.erp.dto.LoginRequest;
import com.erp.dto.LoginResponse;
import com.erp.entity.User;
import com.erp.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void login_success_plaintext() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("password123");

        when(userRepository.findById("admin")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "password123")).thenReturn(false);
        when(jwtUtil.generateToken("admin")).thenReturn("jwt-token");

        LoginRequest request = new LoginRequest("admin", "password123");
        LoginResponse response = authService.login(request);

        assertThat(response.getToken()).isEqualTo("jwt-token");
        assertThat(response.getMessage()).isEqualTo("Username and password is correct!");
    }

    @Test
    void login_success_bcrypt() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("$2a$10$hashedpassword");

        when(userRepository.findById("admin")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "$2a$10$hashedpassword")).thenReturn(true);
        when(jwtUtil.generateToken("admin")).thenReturn("jwt-token");

        LoginRequest request = new LoginRequest("admin", "password123");
        LoginResponse response = authService.login(request);

        assertThat(response.getToken()).isEqualTo("jwt-token");
    }

    @Test
    void login_wrongPassword_throwsBadCredentials() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("correctPassword");

        when(userRepository.findById("admin")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "correctPassword")).thenReturn(false);

        LoginRequest request = new LoginRequest("admin", "wrongPassword");

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessageContaining("Username or password is not correct!");
    }

    @Test
    void login_userNotFound_throwsBadCredentials() {
        when(userRepository.findById("unknown")).thenReturn(Optional.empty());

        LoginRequest request = new LoginRequest("unknown", "password");

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessageContaining("Username or password is not correct!");
    }
}
