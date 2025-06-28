package com.side.tiggle.domain.comment.service

import com.side.tiggle.domain.comment.dto.req.CommentCreateReqDto
import com.side.tiggle.domain.comment.dto.req.CommentUpdateReqDto
import com.side.tiggle.domain.comment.dto.resp.CommentPageRespDto
import com.side.tiggle.domain.comment.dto.resp.CommentRespDto
import com.side.tiggle.domain.transaction.dto.internal.TransactionInfo

interface CommentService {

    fun getParentCount(txId: Long): Int

    fun getParentsByTxId(txId: Long, page: Int, size: Int): CommentPageRespDto

    fun getChildrenByParentId(parentId: Long?, page: Int, size: Int): CommentPageRespDto

    fun createComment(memberId: Long, tx: TransactionInfo, commentDto: CommentCreateReqDto): CommentRespDto

    fun updateComment(memberId: Long, commentId: Long, dto: CommentUpdateReqDto): CommentRespDto

    fun deleteComment(memberId: Long, commentId: Long)
}