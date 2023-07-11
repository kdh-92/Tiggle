package com.side.tiggle.domain.transaction.api;

import com.side.tiggle.domain.transaction.TransactionDto;
import com.side.tiggle.domain.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionApiController {

    private final TransactionService transactionService;

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

    // delete

}
