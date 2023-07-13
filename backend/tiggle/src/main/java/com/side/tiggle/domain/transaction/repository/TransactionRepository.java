package com.side.tiggle.domain.transaction.repository;

import com.side.tiggle.domain.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
