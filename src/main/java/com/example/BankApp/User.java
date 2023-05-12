package com.example.BankApp;

import jakarta.persistence.*;



@Entity
@Table(name = "user_accounts")
public class User {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "balance")
    private Double balance;

    public User() {}

    public User(Long userId, Double balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getUserId() {
        return null;
    }
}