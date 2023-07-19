package com.side.tiggle.domain.transaction.repository;

import com.side.tiggle.domain.transaction.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByMemberId(Long memberId, Pageable pageable);
}
