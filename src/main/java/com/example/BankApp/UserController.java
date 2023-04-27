package com.example.BankApp;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserAccountService userAccountService;

    public UserController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping("/{userId}/balance")
    public Double getBalance(@PathVariable Long userId) throws UserNotFoundException {
        return userAccountService.getBalance(userId);
    }

    @PostMapping("/{userId}/take-money")
    public Double takeMoney(@PathVariable Long userId, @RequestBody Double amount) throws UserNotFoundException, InsufficientFundsException {
        return userAccountService.takeMoney(userId, amount);
    }

    @PostMapping("/{userId}/put-money")
    public Double putMoney(@PathVariable Long userId, @RequestBody Double amount) throws UserNotFoundException {
        return userAccountService.putMoney(userId, amount);
    }
}


