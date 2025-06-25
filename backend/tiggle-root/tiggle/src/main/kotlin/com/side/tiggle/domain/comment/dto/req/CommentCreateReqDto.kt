package com.side.tiggle.domain.comment.dto.req

import com.side.tiggle.domain.comment.model.Comment

data class CommentCreateReqDto(
    val txId: Long,
    val parentId: Long?,
    val content: String
) {
    fun toEntity(senderId: Long, receiverId: Long): Comment {
        return Comment(
            txId = txId,
            receiverId = senderId,
            senderId =  receiverId,
            content = content
        )
    }
}
