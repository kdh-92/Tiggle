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
            AND (:tagNamesJson IS NULL OR JSON_OVERLAPS(t.tag_names, :tagNamesJson))
            ORDER BY t.created_at DESC
        """,
        countQuery = """
            SELECT count(*) FROM transactions t 
            WHERE t.member_id = :memberId
            AND t.deleted = false
            AND (:startDate IS NULL OR t.date >= :startDate)
            AND (:endDate IS NULL OR t.date <= :endDate) 
            AND (:categoryIds IS NULL OR t.category_id IN (:categoryIds))
            AND (:tagNamesJson IS NULL OR JSON_OVERLAPS(t.tag_names, :tagNamesJson))
        """,
        nativeQuery = true
    )
    fun findByMemberIdWithFilters(
        @Param("memberId") memberId: Long,
        @Param("startDate") startDate: LocalDate?,
        @Param("endDate") endDate: LocalDate?,
        @Param("categoryIds") categoryIds: List<Long>?,
        @Param("tagNamesJson") tagNamesJson: String?,
        pageable: Pageable
    ): Page<Transaction>

    @Query("SELECT t FROM Transaction t JOIN FETCH t.member JOIN FETCH t.category ORDER BY t.createdAt DESC")
    fun findAllWithMemberAndCategoryPaged(pageable: Pageable): Page<Transaction>

    @Query("SELECT t FROM Transaction t JOIN FETCH t.member JOIN FETCH t.category WHERE t.id = :id")
    fun findByIdWithMemberAndCategory(@Param("id") id: Long): Transaction?

    @Query(
        value = """
            SELECT t.* FROM transactions t
            WHERE t.deleted = false
            AND (t.content LIKE CONCAT('%', :keyword, '%') OR t.reason LIKE CONCAT('%', :keyword, '%'))
            ORDER BY t.created_at DESC
        """,
        countQuery = """
            SELECT count(*) FROM transactions t
            WHERE t.deleted = false
            AND (t.content LIKE CONCAT('%', :keyword, '%') OR t.reason LIKE CONCAT('%', :keyword, '%'))
        """,
        nativeQuery = true
    )
    fun searchByKeyword(
        @Param("keyword") keyword: String,
        pageable: Pageable
    ): Page<Transaction>

    @Query(
        value = """
            SELECT tx_type, SUM(amount) as total, COUNT(*) as cnt
            FROM transactions
            WHERE member_id = :memberId AND date BETWEEN :startDate AND :endDate AND deleted = false
            GROUP BY tx_type
        """,
        nativeQuery = true
    )
    fun sumByMemberIdAndDateRange(
        @Param("memberId") memberId: Long,
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): List<Array<Any>>

    @Query(
        value = """
            SELECT category_id, SUM(amount) as total
            FROM transactions
            WHERE member_id = :memberId AND date BETWEEN :startDate AND :endDate AND tx_type = 'OUTCOME' AND deleted = false
            GROUP BY category_id ORDER BY total DESC LIMIT 1
        """,
        nativeQuery = true
    )
    fun findTopCategoryByOutcome(
        @Param("memberId") memberId: Long,
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): List<Array<Any>>

    @Query(
        value = """
            SELECT category_id, SUM(amount) as total
            FROM transactions
            WHERE member_id = :memberId AND date BETWEEN :startDate AND :endDate AND tx_type = 'OUTCOME' AND deleted = false
            GROUP BY category_id ORDER BY total DESC
        """,
        nativeQuery = true
    )
    fun findCategoryBreakdownByOutcome(
        @Param("memberId") memberId: Long,
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): List<Array<Any>>
}