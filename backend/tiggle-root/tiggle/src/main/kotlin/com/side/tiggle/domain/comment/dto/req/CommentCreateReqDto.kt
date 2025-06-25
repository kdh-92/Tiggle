package com.side.tiggle.domain.comment.dto.req

import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.transaction.model.Transaction

data class CommentCreateReqDto(
    val txId: Long,
    val parentId: Long?,
    val content: String
) {
    fun toEntity(tx: Transaction, sender: Member): Comment {
        return Comment(
            tx = tx,
            content = this.content,
            receiver = tx.member,
            sender =  sender
        )
    }
}