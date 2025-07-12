package com.side.tiggle.domain.comment.repository

import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.transaction.model.Transaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findAllByTxIdAndParentIdNull(txId: Long, pageable: Pageable): Page<Comment>
    fun findAllByParentId(parentId: Long, pageable: Pageable): Page<Comment>
    fun countAllByTxIdAndParentId(txId: Long, parentId: Long?): Int
    fun countAllByTxId(txId: Long): Int

    @Query("""
        SELECT c FROM Comment c 
        JOIN FETCH c.sender 
        JOIN FETCH c.receiver 
        WHERE c.id = :id
    """)
    fun findByIdWithSender(@Param("id") id: Long): Comment?

    /**
     * 특정 거래의 최상위 댓글 목록을 작성자 정보와 함께 페이지네이션으로 조회합니다.
     *
     * 1. 거래 ID를 기준으로 부모 댓글이 없는 최상위 댓글들을 조회
     * 2. JOIN FETCH를 사용하여 작성자(sender) 정보를 함께 로딩
     * 3. 생성일 기준 내림차순으로 정렬하여 반환
     *
     * @param txId 거래 ID
     * @param pageable 페이지네이션 정보 (페이지 번호, 크기, 정렬 등)
     * @return 작성자 정보가 포함된 댓글 페이지 객체
     * @since 2025-07-04
     * @author 양병학
     */
    @Query("""
        SELECT c FROM Comment c 
        JOIN FETCH c.sender 
        JOIN FETCH c.receiver
        WHERE c.txId = :txId AND c.parentId IS NULL
        ORDER BY c.id DESC
    """)
    fun findByTxIdAndParentIdNullWithSender(
        @Param("txId") txId: Long,
        pageable: Pageable
    ): Page<Comment>

    /**
     * 특정 댓글의 대댓글 목록을 작성자 정보와 함께 페이지네이션으로 조회합니다.
     *
     * 1. 부모 댓글 ID를 기준으로 해당 댓글의 대댓글들을 조회
     * 2. JOIN FETCH를 사용하여 작성자(sender)와 수신자(receiver) 정보를 함께 로딩
     * 3. 생성일 기준 내림차순으로 정렬하여 반환
     *
     * @param parentId 부모 댓글 ID
     * @param pageable 페이지네이션 정보 (페이지 번호, 크기, 정렬 등)
     * @return 작성자와 수신자 정보가 포함된 대댓글 페이지 객체
     * @since 2025-07-04
     * @author 양병학
     */
    @Query("""
        SELECT c FROM Comment c 
        JOIN FETCH c.sender 
        JOIN FETCH c.receiver
        WHERE c.parentId = :parentId
        ORDER BY c.id DESC
    """)
    fun findByParentIdWithSender(
        @Param("parentId") parentId: Long,
        pageable: Pageable
    ): Page<Comment>
}
