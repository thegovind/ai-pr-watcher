package com.aiprwatcher.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {
    
    @NotBlank(message = "Account number is required")
    @Size(min = 6, max = 12, message = "Account number must be between 6 and 12 characters")
    private String accountNumber;
    
    @NotBlank(message = "PIN is required")
    @Size(min = 4, max = 8, message = "PIN must be between 4 and 8 characters")
    private String pin;

    public LoginRequest() {}

    public LoginRequest(String accountNumber, String pin) {
        this.accountNumber = accountNumber;
        this.pin = pin;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
