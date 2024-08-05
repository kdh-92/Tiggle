package com.side.tiggle.domain.transaction.repository

import com.side.tiggle.domain.transaction.model.Transaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface TransactionRepository: JpaRepository<Transaction, Long> {

    fun findByMemberId(memberId: Long, pageable: Pageable): Page<Transaction>
    fun findByDateBetweenOrderByDateAsc(startDate: LocalDate, endDate: LocalDate): List<Transaction>
}
