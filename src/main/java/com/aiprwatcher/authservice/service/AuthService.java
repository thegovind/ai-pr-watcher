package com.aiprwatcher.authservice.service;

import com.aiprwatcher.authservice.dto.LoginRequest;
import com.aiprwatcher.authservice.dto.LoginResponse;
import com.aiprwatcher.authservice.entity.User;
import com.aiprwatcher.authservice.repository.UserRepository;
import com.aiprwatcher.authservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final int LOCKOUT_DURATION_MINUTES = 30;

    public LoginResponse authenticate(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByAccountNumber(loginRequest.getAccountNumber());
        
        if (userOptional.isEmpty()) {
            return new LoginResponse(false, "Invalid account number or PIN");
        }
        
        User user = userOptional.get();
        
        if (!user.isActive()) {
            return new LoginResponse(false, "Account is deactivated");
        }
        
        if (user.isLocked()) {
            return new LoginResponse(false, "Account is temporarily locked due to multiple failed login attempts");
        }
        
        if (!passwordEncoder.matches(loginRequest.getPin(), user.getHashedPin())) {
            handleFailedLogin(user);
            return new LoginResponse(false, "Invalid account number or PIN");
        }
        
        handleSuccessfulLogin(user);
        
        String token = jwtUtil.generateToken(user.getAccountNumber());
        long expiresIn = jwtUtil.getExpirationTime();
        
        return new LoginResponse(true, "Authentication successful", token, user.getAccountNumber(), expiresIn);
    }
    
    private void handleFailedLogin(User user) {
        user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
        
        if (user.getFailedLoginAttempts() >= MAX_FAILED_ATTEMPTS) {
            user.setLockedUntil(LocalDateTime.now().plusMinutes(LOCKOUT_DURATION_MINUTES));
        }
        
        userRepository.save(user);
    }
    
    private void handleSuccessfulLogin(User user) {
        user.setFailedLoginAttempts(0);
        user.setLockedUntil(null);
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
