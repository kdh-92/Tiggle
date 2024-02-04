package com.side.tiggle.domain.transaction.api

import com.side.tiggle.domain.comment.service.CommentService
import com.side.tiggle.domain.transaction.service.TransactionService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/transaction")
class TransactionApiController(
    private val transactionService: TransactionService,
    private val commentService: CommentService,
    private val reactionService
) {

}