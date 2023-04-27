package com.example.BankApp;

import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public Double getBalance(Long userId) {
        Optional<User> userAccount = userAccountRepository.findById(userId);
        return userAccount.map(User::getBalance).orElse(-1.0);
    }

    public Double takeMoney(Long userId, Double amount) {
        Optional<User> userAccount = userAccountRepository.findById(userId);
        if (userAccount.isPresent()) {
            User account = userAccount.get();
            Double balance = account.getBalance();
            if (balance >= amount) {
                account.setBalance(balance - amount);
                userAccountRepository.save(account);
                return amount;
            }
        }
        return -1.0;
    }

    public Double putMoney(Long userId, Double amount) {
        Optional<User> userAccount = userAccountRepository.findById(userId);
        if (userAccount.isPresent()) {
            User account = userAccount.get();
            Double balance = account.getBalance();
            account.setBalance(balance + amount);
            userAccountRepository.save(account);
            return amount;
        }
        return -1.0;
    }
}


