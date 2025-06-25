package com.side.tiggle.domain.comment.dto.resp

data class CommentPageRespDto(
    val comments: List<CommentChildRespDto>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Long,
    val totalPages: Int,
    val isLast: Boolean
)
