package com.banking.accountservice.dto;

import java.math.BigDecimal;

public class TransactionResponse {
    private String accountId;
    private BigDecimal amount;
    private BigDecimal newBalance;
    private String message;

    public TransactionResponse() {}

    public TransactionResponse(String accountId, BigDecimal amount, BigDecimal newBalance, String message) {
        this.accountId = accountId;
        this.amount = amount;
        this.newBalance = newBalance;
        this.message = message;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
