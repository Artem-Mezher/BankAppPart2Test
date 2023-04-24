package com.example.BankApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserAccountRepository userRepository;

    @GetMapping("/{userId}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getBalance());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}/take")
    public ResponseEntity<Double> takeMoney(@PathVariable Long userId, @RequestParam Double amount) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Double balance = user.get().getBalance();
            if (balance >= amount) {
                user.get().setBalance(balance - amount);
                userRepository.save(user.get());
                return ResponseEntity.ok(user.get().getBalance());
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}/put")
    public ResponseEntity<Double> putMoney(@PathVariable Long userId, @RequestParam Double amount) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Double balance = user.get().getBalance();
            user.get().setBalance(balance + amount);
            userRepository.save(user.get());
            return ResponseEntity.ok(user.get().getBalance());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

