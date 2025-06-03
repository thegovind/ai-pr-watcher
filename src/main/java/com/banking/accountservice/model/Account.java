package com.banking.accountservice.model;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Account {
    private final String id;
    private BigDecimal balance;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public Account(String id, BigDecimal initialBalance) {
        this.id = id;
        this.balance = initialBalance != null ? initialBalance : BigDecimal.ZERO;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        lock.readLock().lock();
        try {
            return balance;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        
        lock.writeLock().lock();
        try {
            balance = balance.add(amount);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        
        lock.writeLock().lock();
        try {
            if (balance.compareTo(amount) < 0) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            balance = balance.subtract(amount);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
