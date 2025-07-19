package com.side.tiggle.domain.transaction.repository

import com.side.tiggle.domain.transaction.model.Transaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface TransactionRepository: JpaRepository<Transaction, Long> {


    @Query(
        value = """
            SELECT t.* FROM transactions t 
            WHERE t.member_id = :memberId
            AND t.deleted = false
            AND (:startDate IS NULL OR t.date >= :startDate)
            AND (:endDate IS NULL OR t.date <= :endDate) 
            AND (:categoryIds IS NULL OR t.category_id IN (:categoryIds))
            AND (:tagNames IS NULL OR JSON_OVERLAPS(t.tag_names, CAST(:tagNames AS JSON)))
            ORDER BY t.created_at DESC
        """, nativeQuery = true
    )
    fun findByMemberIdWithFilters(
        @Param("memberId") memberId: Long,
        @Param("startDate") startDate: LocalDate?,
        @Param("endDate") endDate: LocalDate?,
        @Param("categoryIds") categoryIds: List<Long>?,
        @Param("tagNames") tagNames: List<String>?,
        pageable: Pageable
    ): Page<Transaction>

    @Query("SELECT t FROM Transaction t JOIN FETCH t.member JOIN FETCH t.category ORDER BY t.createdAt DESC")
    fun findAllWithMemberAndCategoryPaged(pageable: Pageable): Page<Transaction>

    @Query("SELECT t FROM Transaction t JOIN FETCH t.member JOIN FETCH t.category WHERE t.id = :id")
    fun findByIdWithMemberAndCategory(@Param("id") id: Long): Transaction?
}