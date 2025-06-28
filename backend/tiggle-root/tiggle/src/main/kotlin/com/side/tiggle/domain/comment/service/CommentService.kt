package com.side.tiggle.domain.comment.service

import com.side.tiggle.domain.comment.dto.req.CommentCreateReqDto
import com.side.tiggle.domain.comment.dto.req.CommentUpdateReqDto
import com.side.tiggle.domain.comment.dto.resp.CommentChildRespDto
import com.side.tiggle.domain.comment.dto.resp.CommentPageRespDto
import com.side.tiggle.domain.comment.dto.resp.CommentRespDto
import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.comment.repository.CommentRepository
import com.side.tiggle.domain.notification.service.NotificationService
import com.side.tiggle.domain.transaction.dto.internal.TransactionInfo
import com.side.tiggle.global.exception.NotAuthorizedException
import com.side.tiggle.global.exception.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CommentService(
    val commentRepository: CommentRepository,
    val notificationService: NotificationService,
) {
    fun getParentCount(txId: Long): Int {
        return commentRepository.countAllByTxIdAndParentId(txId, null)
    }

    fun getChildCommentCount(txId: Long, parentId: Long): Int {
        return commentRepository.countAllByTxIdAndParentId(txId, parentId)
    }

    fun getCommentCount(txId: Long): Int {
        return commentRepository.countAllByTxId(txId)
    }

    fun getParentsByTxId(txId: Long, page: Int, size: Int): CommentPageRespDto {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id")
        val pageComment = commentRepository.findAllByTxIdAndParentIdNull(txId, pageable)
        return toCommentPageRespDto(pageComment)
    }

    fun getChildrenByParentId(parentId: Long?, page: Int, size: Int): CommentPageRespDto {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id")
        val pageComment = commentRepository.findAllByParentId(parentId!!, pageable)
        return toCommentPageRespDto(pageComment)
    }

    private fun toCommentPageRespDto(page: Page<Comment>): CommentPageRespDto {
        val commentListChildRespDto = page.content.map {
            val childCount = getChildCommentCount(it.txId, it.id)
            CommentChildRespDto.fromEntity(it, childCount)
        }

        return CommentPageRespDto(
            comments = commentListChildRespDto,
            pageNumber = page.number,
            pageSize = page.size,
            totalElements = page.totalElements,
            totalPages = page.totalPages,
            isLast = page.isLast
        )
    }

    fun findParentCommentOrThrow(parentId: Long): Comment? {
        return commentRepository.findById(parentId).orElseThrow { NotFoundException() }
    }

    fun createComment(memberId: Long, tx: TransactionInfo, commentDto: CommentCreateReqDto): CommentRespDto {
        val parentComment = commentDto.parentId?.let { findParentCommentOrThrow(commentDto.parentId) }
        val comment: Comment = commentDto.toEntity(memberId, tx.memberId)
        val savedComment = commentRepository.save(comment)

        notificationService.sendCommentNotification(savedComment, parentComment, tx, memberId)

        return CommentRespDto.fromEntity(savedComment)
    }

    fun updateComment(memberId: Long, commentId: Long, dto: CommentUpdateReqDto): CommentRespDto {
        val comment = commentRepository.findById(commentId)
            .orElseThrow { NotFoundException() }

        if (comment.senderId != memberId) {
            throw NotAuthorizedException()
        }

        comment.content = dto.content
        val updated = commentRepository.save(comment)

        return CommentRespDto.fromEntity(updated)
    }

    fun deleteComment(memberId: Long, commentId: Long) {
        val comment = commentRepository.findById(commentId)
            .filter { it.senderId == memberId }
            .orElseThrow { NotFoundException() }
        commentRepository.delete(comment)
    }
}
