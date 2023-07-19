package com.side.tiggle.domain.transaction.api;

import com.side.tiggle.domain.transaction.dto.TransactionDto;
import com.side.tiggle.domain.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionApiController {

    private final TransactionService transactionService;
    private final int defaultCount = 5;

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto) {
        return new ResponseEntity<>(transactionService.createTransaction(transactionDto), HttpStatus.CREATED);
    }

    /**
     * tx 상세 조회
     * @param transactionId
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable("id") Long transactionId) {
        return new ResponseEntity<>(transactionService.getTransaction(transactionId), HttpStatus.OK);
    }

    /**
     * 최신 n개 & page 처리 된 tx 조회
     * n개의 tx가 있고 n개를 넘긴 tx page 조회 요청이 있을 때 빈 배열이 반환되는데 이 부분에 대해 어떻게 처리할지 논의하기
     *
     * @param count
     * @param offset
     * @return
     */

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getCountOffsetTransaction(@NotBlank @RequestParam("count") int count, @RequestParam(defaultValue = "0") int offset) {
        if (count > 0) return new ResponseEntity<>(transactionService.getCountOffsetTransaction(count, offset), HttpStatus.OK);
        else return new ResponseEntity<>(transactionService.getCountOffsetTransaction(defaultCount, offset), HttpStatus.OK);
    }

    /**
     * 특정 memberId에 해당하는 tx 조회
     * @param memberId
     * @param count
     * @param offset
     * @return
     */
    @GetMapping("/member")
    public ResponseEntity<List<TransactionDto>> getMemberCountOffsetTransaction(@NotBlank @RequestParam Long memberId, @NotBlank @RequestParam("count") int count, @RequestParam(defaultValue = "0") int offset) {
        if (count > 0) return new ResponseEntity<>(transactionService.getMemberCountOffsetTransaction(memberId, count, offset), HttpStatus.OK);
        else return new ResponseEntity<>(transactionService.getMemberCountOffsetTransaction(memberId, defaultCount, offset), HttpStatus.OK);
    }



    @GetMapping("/all")
    public ResponseEntity<List<TransactionDto>> getAllTransaction() {
        return new ResponseEntity<>(transactionService.getAllTransaction(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto> updateTransaction(@PathVariable("id") Long transactionId,
                                                    @RequestBody TransactionDto transactionDto) {
        return new ResponseEntity<>(transactionService.updateTransaction(transactionId, transactionDto), HttpStatus.OK);
    }

    // delete

}
