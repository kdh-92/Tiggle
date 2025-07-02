package com.side.tiggle.domain.transaction.repository

import com.side.tiggle.domain.transaction.model.Transaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface TransactionRepository: JpaRepository<Transaction, Long> {



    @Query("""
        SELECT t FROM Transaction t JOIN FETCH t.member
        WHERE t.member.id = :memberId
        AND (:startDate IS NULL OR t.date >= :startDate)
        AND (:endDate IS NULL OR t.date <= :endDate) 
        AND (:categoryIds IS NULL OR t.categoryId IN :categoryIds)
        AND (:tagNames IS NULL OR EXISTS (
            SELECT 1 FROM t.tagNames tag WHERE tag IN :tagNames
        ))
        ORDER BY t.createdAt DESC
    """)
    fun findByMemberIdWithFilters(
        @Param("memberId") memberId: Long,
        @Param("startDate") startDate: LocalDate?,
        @Param("endDate") endDate: LocalDate?,
        @Param("categoryIds") categoryIds: List<Long>?,
        @Param("tagNames") tagNames: List<String>?,
        pageable: Pageable
    ): Page<Transaction>

    @Query("SELECT t FROM Transaction t JOIN FETCH t.member ORDER BY t.createdAt DESC")
    fun findAllWithMember(): List<Transaction>

    @Query("SELECT t FROM Transaction t JOIN FETCH t.member ORDER BY t.createdAt DESC")
    fun findAllWithMemberPaged(pageable: Pageable): Page<Transaction>
}