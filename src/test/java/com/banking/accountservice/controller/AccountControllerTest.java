package com.banking.accountservice.controller;

import com.banking.accountservice.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getBalance_ShouldReturnBalance() throws Exception {
        when(accountService.getBalance("1")).thenReturn(new BigDecimal("1000.00"));

        mockMvc.perform(get("/accounts/1/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value("1"))
                .andExpect(jsonPath("$.balance").value(1000.00));
    }

    @Test
    void deposit_ShouldReturnUpdatedBalance() throws Exception {
        when(accountService.deposit(eq("1"), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("1100.00"));

        String requestBody = "{\"amount\": 100.00}";

        mockMvc.perform(post("/accounts/1/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value("1"))
                .andExpect(jsonPath("$.amount").value(100.00))
                .andExpect(jsonPath("$.newBalance").value(1100.00))
                .andExpect(jsonPath("$.message").value("Deposit successful"));
    }

    @Test
    void withdraw_ShouldReturnUpdatedBalance() throws Exception {
        when(accountService.withdraw(eq("1"), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("900.00"));

        String requestBody = "{\"amount\": 100.00}";

        mockMvc.perform(post("/accounts/1/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value("1"))
                .andExpect(jsonPath("$.amount").value(100.00))
                .andExpect(jsonPath("$.newBalance").value(900.00))
                .andExpect(jsonPath("$.message").value("Withdrawal successful"));
    }

    @Test
    void deposit_WithInvalidAmount_ShouldReturnBadRequest() throws Exception {
        String requestBody = "{\"amount\": -100.00}";

        mockMvc.perform(post("/accounts/1/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}
