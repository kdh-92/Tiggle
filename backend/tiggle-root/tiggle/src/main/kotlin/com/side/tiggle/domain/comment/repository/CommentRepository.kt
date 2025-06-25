package com.side.tiggle.domain.comment.repository

import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.transaction.model.Transaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findAllByTxAndParentIdNull(tx: Transaction, pageable: Pageable): Page<Comment>
    fun findAllByParentId(parentId: Long, pageable: Pageable): Page<Comment>
    fun countAllByTxAndParentId(tx: Transaction, parentId: Long?): Int
    fun countAllByTx(tx: Transaction): Int
}
