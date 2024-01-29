package com.side.tiggle.domain.comment.dto

import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.transaction.model.Transaction
import javax.validation.constraints.Size

open class CommentDto(
    val txId: Long,
    val parentId: Long?,
    var senderId: Long,
    val receiverId: Long,
    @Size(max = 255)
    val content: String
) {

    fun toEntity(tx: Transaction, sender: Member, receiver: Member): Comment{
        return Comment(
            tx = tx,
            parentId = parentId,
            receiver = receiver,
            sender = sender,
            content = content
        )
    }
    companion object {
        fun fromEntity(comment: Comment): CommentDto {
            return CommentDto(
                txId = comment.tx.id,
                parentId = comment.parentId,
                senderId = comment.sender.id,
                receiverId = comment.receiver.id,
                content = comment.content
            )
        }
    }
}