package com.side.tiggle.domain.comment.service

import com.side.tiggle.domain.comment.dto.req.CommentCreateReqDto
import com.side.tiggle.domain.comment.dto.req.CommentUpdateReqDto
import com.side.tiggle.domain.comment.dto.resp.CommentChildRespDto
import com.side.tiggle.domain.comment.dto.resp.CommentPageRespDto
import com.side.tiggle.domain.comment.dto.resp.CommentRespDto
import com.side.tiggle.domain.comment.exception.CommentException
import com.side.tiggle.domain.comment.exception.error.CommentErrorCode
import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.comment.repository.CommentRepository
import com.side.tiggle.domain.notification.service.NotificationService
import com.side.tiggle.domain.transaction.dto.internal.TransactionInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val notificationService: NotificationService,
) : CommentService {

    override fun getParentCount(txId: Long): Int {
        return commentRepository.countAllByTxIdAndParentId(txId, null)
    }

    override fun getParentsByTxId(txId: Long, page: Int, size: Int): CommentPageRespDto {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id")
        val pageComment = commentRepository.findByTxIdAndParentIdNullWithSender(txId, pageable)

        return toCommentPageRespDto(pageComment)
    }

    override fun getChildrenByParentId(parentId: Long, page: Int, size: Int): CommentPageRespDto {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id")
        val pageComment = commentRepository.findByParentIdWithSender(parentId, pageable)

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

    override fun createComment(memberId: Long, tx: TransactionInfo, commentDto: CommentCreateReqDto): CommentRespDto {
        val parentComment = commentDto.parentId?.let { findParentCommentOrThrow(commentDto.parentId) }
        val comment: Comment = commentDto.toEntity(memberId, tx.memberId)
        val savedComment = commentRepository.save(comment)

        notificationService.sendCommentNotification(savedComment, parentComment, tx, memberId)

        return CommentRespDto.fromEntity(savedComment)
    }

    override fun updateComment(memberId: Long, commentId: Long, dto: CommentUpdateReqDto): CommentRespDto {
        val comment = commentRepository.findByIdWithSender(commentId)
            ?: throw CommentException(CommentErrorCode.COMMENT_NOT_FOUND)

        if (comment.sender.id != memberId) {
            throw CommentException(CommentErrorCode.COMMENT_ACCESS_DENIED)
        }

        comment.content = dto.content
        val updated = commentRepository.save(comment)

        return CommentRespDto.fromEntity(updated)
    }

    override fun deleteComment(memberId: Long, commentId: Long) {
        val comment = commentRepository.findByIdWithSender(commentId)
            ?: throw CommentException(CommentErrorCode.COMMENT_NOT_FOUND)

        if (comment.sender.id != memberId) {
            throw CommentException(CommentErrorCode.COMMENT_ACCESS_DENIED)
        }

        commentRepository.delete(comment)
    }

    private fun getChildCommentCount(txId: Long, parentId: Long): Int {
        return commentRepository.countAllByTxIdAndParentId(txId, parentId)
    }

    private fun findParentCommentOrThrow(parentId: Long): Comment {
        return commentRepository.findByIdWithSender(parentId)
            ?: throw CommentException(CommentErrorCode.COMMENT_NOT_FOUND)
    }
}