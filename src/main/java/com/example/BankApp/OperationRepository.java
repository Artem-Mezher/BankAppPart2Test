package com.example.BankApp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findByUserId(Long userId);

    List<Operation> findByUserIdAndDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    List<Operation> findByUserIdAndDateAfter(Long userId, LocalDateTime startDate);

    List<Operation> findByUserIdAndDateBefore(Long userId, LocalDateTime endDate);
}

