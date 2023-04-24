package com.example.BankApp;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<User, Long> {
    User findById(long id);

    void takeMoney(long id, double amount);

    void putMoney(long id, double amount);
}
