package com.example.BankApp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Configuration
@ComponentScan("com.example.BankApp")
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;


    private final OperationRepository operationRepository;

    public UserAccountService(UserAccountRepository userAccountRepository, OperationRepository operationRepository) {
        this.userAccountRepository = userAccountRepository;
        this.operationRepository = operationRepository;
    }

    public Double getBalance(Long userId) {
        Optional<User> userAccount = userAccountRepository.findById(userId);
        return userAccount.map(User::getBalance).orElse(-1.0);
    }

    @Transactional
    public Double takeMoney(Long userId, Double amount) throws UserNotFoundException, InsufficientFundsException {
        User account = getUserAccountById(userId);
        Double balance = account.getBalance();
        if (balance >= amount) {
            account.setBalance(balance - amount);
            userAccountRepository.save(account);
            saveOperation(account, OperationType.WITHDRAWAL, amount);
            return amount;
        }
        throw new InsufficientFundsException("Insufficient funds for withdrawal");
    }

    @Transactional
    public Double putMoney(Long userId, Double amount) throws UserNotFoundException {
        User account = getUserAccountById(userId);
        Double balance = account.getBalance();
        account.setBalance(balance + amount);
        userAccountRepository.save(account);
        saveOperation(account, OperationType.DEPOSIT, amount);
        return amount;
    }
    public List<Operation> getOperationList(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate != null && endDate != null) {
            return operationRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
        } else if (startDate != null) {
            return operationRepository.findByUserIdAndDateAfter(userId, startDate);
        } else if (endDate != null) {
            return operationRepository.findByUserIdAndDateBefore(userId, endDate);
        } else {
            return operationRepository.findByUserId(userId);
        }
    }

    @Transactional
    public void transferMoney(Long senderId, Long recipientId, Double amount) throws UserNotFoundException, InsufficientFundsException {
        User senderAccount = getUserAccountById(senderId);
        User recipientAccount = getUserAccountById(recipientId);

        Double senderBalance = senderAccount.getBalance();
        if (senderBalance >= amount) {
            senderAccount.setBalance(senderBalance - amount);
            recipientAccount.setBalance(recipientAccount.getBalance() + amount);

            userAccountRepository.save(senderAccount);
            userAccountRepository.save(recipientAccount);

            saveOperation(senderAccount, OperationType.TRANSFER_OUT, amount);
            saveOperation(recipientAccount, OperationType.TRANSFER_IN, amount);
        } else {
            throw new InsufficientFundsException("Insufficient funds for transfer");
        }
    }

    private User getUserAccountById(Long userId) throws UserNotFoundException {
        return userAccountRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private void saveOperation(User account, OperationType operationType, Double amount) {
        Operation operation = new Operation(account.getUserId(), operationType, amount);
        operationRepository.save(operation);
    }
}


