package com.example.BankApp;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    @GetMapping("/users/{userId}/operations")
    public List<Operation> getOperationList(
            @PathVariable Long userId,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate) {
        return userAccountService.getOperationList(userId, startDate, endDate);
    }
    @PostMapping("/{senderId}/transfer-money")
    public void transferMoney(
            @PathVariable Long senderId,
            @RequestParam Long recipientId,
            @RequestParam Double amount
    ) throws UserNotFoundException, InsufficientFundsException {
        userAccountService.transferMoney(senderId, recipientId, amount);
    }
}
