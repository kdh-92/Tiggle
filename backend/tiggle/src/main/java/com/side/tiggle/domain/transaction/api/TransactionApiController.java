package com.side.tiggle.domain.transaction.api;

import com.side.tiggle.domain.comment.dto.resp.CommentRespDto;
import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.comment.service.CommentService;
import com.side.tiggle.domain.transaction.TransactionDto;
import com.side.tiggle.domain.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionApiController {

    private final TransactionService transactionService;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<TransactionDto> createComment(@RequestBody TransactionDto transactionDto) {
        return new ResponseEntity<>(transactionService.createTransaction(transactionDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getComment(@PathVariable("id") Long transactionId) {
        return new ResponseEntity<>(transactionService.getTransaction(transactionId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransactionDto>> getAllComment() {
        return new ResponseEntity<>(transactionService.getAllTransaction(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto> updateComment(@PathVariable("id") Long transactionId,
                                                    @RequestBody TransactionDto transactionDto) {
        return new ResponseEntity<>(transactionService.updateTransaction(transactionId, transactionDto), HttpStatus.OK);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<Page<CommentRespDto>> getAllCommentsByTx(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ){
        Page<Comment> pagedComments = commentService.getParentsByTxId(id, page, size);
        Page<CommentRespDto> pagedResult = CommentRespDto.fromEntityPage(pagedComments);
        return new ResponseEntity<>(pagedResult, HttpStatus.OK);
    }
}
