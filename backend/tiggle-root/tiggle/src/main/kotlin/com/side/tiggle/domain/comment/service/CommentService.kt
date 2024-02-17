package com.side.tiggle.domain.comment.service

import com.side.tiggle.domain.comment.dto.req.CommentCreateReqDto
import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.comment.repository.CommentRepository
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.member.repository.MemberRepository
import com.side.tiggle.domain.transaction.service.TransactionService
import com.side.tiggle.domain.notification.NotificationProducer
import com.side.tiggle.domain.notification.dto.NotificationProduceDto
import com.side.tiggle.global.exception.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CommentService(
    val commentRepository: CommentRepository,
    val memberRepository: MemberRepository,
    val transactionService: TransactionService,
    val notificationProducer: NotificationProducer
) {
    fun getById(id: Long): Comment {
        return commentRepository.findById(id).orElseThrow { NotFoundException() }
    }

    fun getParentCount(txId: Long): Int {
        val transaction = transactionService.getTransaction(txId)
        return commentRepository.countAllByTxAndParentId(transaction, null)
    }

    fun getChildCount(txId: Long, parentId: Long): Int {
        val transaction = transactionService.getTransaction(txId)
        return commentRepository.countAllByTxAndParentId(transaction, parentId)
    }

    fun getCommentCount(txId: Long): Int {
        val transaction = transactionService.getTransaction(txId)
        return commentRepository.countAllByTx(transaction)
    }

    fun getParentsByTxId(txId: Long, page: Int, size: Int): Page<Comment> {
        val tx = transactionService.getTransaction(txId)
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id")
        return commentRepository.findAllByTxAndParentIdNull(tx, pageable)
    }

    fun getChildrenByParentId(parentId: Long?, page: Int, size: Int): Page<Comment> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id")
        return commentRepository.findAllByParentId(parentId!!, pageable)
    }

    fun createComment(memberId: Long, commentDto: CommentCreateReqDto): Comment {
        // TODO : Service 메소드로 수정한다
        val tx = transactionService.getTransaction(commentDto.txId)
        val sender: Member = memberRepository.findById(memberId).orElseThrow { NotFoundException() }

        val parentComment = if (commentDto.parentId != null) {
            commentRepository.findById(commentDto.parentId).orElseThrow { NotFoundException() }
        } else {
            null
        }

        val comment: Comment = commentDto.toEntity(tx, sender)
        commentRepository.save(comment)

        if (parentComment != null) {
            // 답댓글
            notificationProducer.send(
                NotificationProduceDto(
                    imageUrl = null,
                    content = comment.content,
                    receiverId = parentComment.sender.id,
                    senderId = memberId,
                    title = tx.content,
                    txId = tx.id,
                    commentId = comment.id,
                    type = NotificationProduceDto.Type.REPLY
                )
            )
        } else {
            notificationProducer.send(
                NotificationProduceDto(
                    imageUrl = null,
                    content = comment.content,
                    receiverId = tx.member.id,
                    senderId = memberId,
                    title = tx.content,
                    txId = tx.id,
                    commentId = comment.id,
                    type = NotificationProduceDto.Type.COMMENT
                )
            )
        }
        return comment
    }

    fun  updateComment(memberId: Long, commentId: Long, content: String): Comment {
        val comment = commentRepository.findById(commentId)
            .filter {
                it.sender.id == memberId
            }
            .orElseThrow { NotFoundException() }
            .apply {
                this.content = content
            }
        return commentRepository.save(comment)
    }

    fun deleteComment(memberId: Long, commentId: Long) {
        val comment = commentRepository.findById(commentId)
            .filter { it.sender.id == memberId }
            .orElseThrow { NotFoundException() }
        commentRepository.delete(comment)
    }
}