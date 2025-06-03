package com.banking.accountservice.service;

import com.banking.accountservice.exception.AccountNotFoundException;
import com.banking.accountservice.exception.InsufficientBalanceException;
import com.banking.accountservice.model.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class AccountService {
    private final ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<>();

    public AccountService() {
        accounts.put("1", new Account("1", new BigDecimal("1000.00")));
        accounts.put("2", new Account("2", new BigDecimal("500.00")));
        accounts.put("3", new Account("3", new BigDecimal("0.00")));
    }

    public BigDecimal getBalance(String accountId) {
        Account account = getAccount(accountId);
        return account.getBalance();
    }

    public BigDecimal deposit(String accountId, BigDecimal amount) {
        Account account = getAccount(accountId);
        try {
            account.deposit(amount);
            return account.getBalance();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public BigDecimal withdraw(String accountId, BigDecimal amount) {
        Account account = getAccount(accountId);
        try {
            account.withdraw(amount);
            return account.getBalance();
        } catch (IllegalArgumentException ex) {
            if (ex.getMessage().contains("Insufficient balance")) {
                throw new InsufficientBalanceException(ex.getMessage());
            }
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    private Account getAccount(String accountId) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new AccountNotFoundException(accountId);
        }
        return account;
    }

    public boolean accountExists(String accountId) {
        return accounts.containsKey(accountId);
    }
}
