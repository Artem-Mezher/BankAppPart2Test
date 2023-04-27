package com.example.BankApp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<User, Long> {

   User findByUserId(Long userId);
    User save(User user);
}


