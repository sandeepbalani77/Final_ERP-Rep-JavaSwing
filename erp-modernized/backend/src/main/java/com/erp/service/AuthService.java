package com.erp.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.erp.config.JwtUtil;
import com.erp.dto.LoginRequest;
import com.erp.dto.LoginResponse;
import com.erp.entity.User;
import com.erp.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Username or password is not correct!"));

        // For backward compat: support both plaintext (legacy) and BCrypt passwords.
        // Check if stored password is a BCrypt hash first to prevent hash-as-password attacks.
        boolean matches;
        if (user.getPassword().startsWith("$2a$") || user.getPassword().startsWith("$2b$") || user.getPassword().startsWith("$2y$")) {
            matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        } else {
            matches = request.getPassword().equals(user.getPassword());
        }

        if (!matches) {
            throw new BadCredentialsException("Username or password is not correct!");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return new LoginResponse(token, "Username and password is correct!");
    }
}
