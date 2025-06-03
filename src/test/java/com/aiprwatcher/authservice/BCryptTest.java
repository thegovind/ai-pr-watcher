package com.aiprwatcher.authservice;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String plaintext = "1234";
        
        String newHash = encoder.encode(plaintext);
        System.out.println("Generated BCrypt hash for PIN '1234': " + newHash);
        System.out.println("Verification test: " + encoder.matches(plaintext, newHash));
        
        String currentHash = "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi";
        System.out.println("Current hash matches: " + encoder.matches(plaintext, currentHash));
    }
}
