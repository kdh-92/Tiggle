package com.side.tiggle.domain.comment.dto.req

import com.side.tiggle.domain.comment.dto.CommentDto

class CommentCreateReqDto(
    txId: Long,
    parentId: Long?,
    senderId: Long,
    receiverId: Long,
    content: String
): CommentDto(
    txId, parentId, senderId, receiverId, content
)