package com.side.tiggle.domain.transaction.api;

import com.side.tiggle.domain.member.MemberDto;
import com.side.tiggle.domain.transaction.dto.TransactionDto;
import com.side.tiggle.domain.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionApiController {

    private final TransactionService transactionService;
    private final String DEFAULT_COUNT = "5";
    private final String DEFAULT_OFFSET = "0";

    @PostMapping
    public ResponseEntity<TransactionDto.TransactionResponseDto> createTransaction(@RequestBody TransactionDto.TransactionRequestDto dto) {
        return new ResponseEntity<>(
                TransactionDto.fromEntity(transactionService.createTransaction(dto)),
                HttpStatus.CREATED
        );
    }

    /**
     * tx 상세 조회
     * @param transactionId
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto.TransactionResponseDto> getTransaction(@PathVariable("id") Long transactionId) {
        return new ResponseEntity<>(
                TransactionDto.fromEntity(transactionService.getTransaction(transactionId)),
                HttpStatus.OK
        );
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
    public ResponseEntity<List<TransactionDto.TransactionResponseDto>> getCountOffsetTransaction(@NotBlank @RequestParam(defaultValue = DEFAULT_COUNT) int count, @RequestParam(defaultValue = DEFAULT_OFFSET) int offset) {
        return new ResponseEntity<>(
                transactionService.getCountOffsetTransaction(count, offset)
                        .stream()
                        .map(tx -> TransactionDto.fromEntity(tx))
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    /**
     * 특정 memberId에 해당하는 tx 조회
     * @param memberId
     * @param count
     * @param offset
     * @return
     */
    @GetMapping("/member")
    public ResponseEntity<List<TransactionDto.TransactionResponseDto>> getMemberCountOffsetTransaction(@NotBlank @RequestParam Long memberId, @NotBlank @RequestParam(defaultValue = DEFAULT_COUNT) int count, @RequestParam(defaultValue = DEFAULT_OFFSET) int offset) {
        return new ResponseEntity<>(
                transactionService.getMemberCountOffsetTransaction(memberId, count, offset)
                        .stream()
                        .map(tx -> TransactionDto.fromEntity(tx))
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }



    @GetMapping("/all")
    public ResponseEntity<List<TransactionDto.TransactionResponseDto>> getAllTransaction() {
        return new ResponseEntity<>(
                transactionService.getAllTransaction()
                        .stream()
                        .map(tx -> TransactionDto.fromEntity(tx))
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto.TransactionResponseDto> updateTransaction(@PathVariable("id") Long transactionId,
                                                                                   @RequestBody TransactionDto.TransactionUpdateRequestDto dto) {
        return new ResponseEntity<>(
                TransactionDto.fromEntity(transactionService.updateTransaction(transactionId, dto)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(
            @AuthenticationPrincipal MemberDto memberDto,
            @PathVariable("id") Long transactionId
    ) {
        transactionService.deleteTransaction(memberDto.getId(), transactionId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
