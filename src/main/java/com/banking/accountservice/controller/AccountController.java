package com.banking.accountservice.controller;

import com.banking.accountservice.dto.BalanceResponse;
import com.banking.accountservice.dto.TransactionRequest;
import com.banking.accountservice.dto.TransactionResponse;
import com.banking.accountservice.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable String id) {
        BigDecimal balance = accountService.getBalance(id);
        BalanceResponse response = new BalanceResponse(id, balance);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<TransactionResponse> deposit(
            @PathVariable String id,
            @Valid @RequestBody TransactionRequest request) {
        
        BigDecimal newBalance = accountService.deposit(id, request.getAmount());
        TransactionResponse response = new TransactionResponse(
                id, 
                request.getAmount(), 
                newBalance, 
                "Deposit successful"
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(
            @PathVariable String id,
            @Valid @RequestBody TransactionRequest request) {
        
        BigDecimal newBalance = accountService.withdraw(id, request.getAmount());
        TransactionResponse response = new TransactionResponse(
                id, 
                request.getAmount(), 
                newBalance, 
                "Withdrawal successful"
        );
        return ResponseEntity.ok(response);
    }
}
