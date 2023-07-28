package com.side.tiggle.domain.transaction.api;

import com.side.tiggle.domain.comment.dto.resp.CommentRespDto;
import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.comment.service.CommentService;
import com.side.tiggle.domain.member.MemberDto;
import com.side.tiggle.domain.transaction.dto.TransactionDto;
import com.side.tiggle.domain.transaction.dto.req.TransactionUpdateReqDto;
import com.side.tiggle.domain.transaction.dto.resp.TransactionRespDto;
import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.domain.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final CommentService commentService;
    private final String DEFAULT_INDEX = "0";
    private final String DEFAULT_PAGE_SIZE = "5";


    @PostMapping
    public ResponseEntity<TransactionRespDto> createTransaction(@RequestBody TransactionDto dto) {
        return new ResponseEntity<>(
                TransactionRespDto.fromEntity(transactionService.createTransaction(dto)),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "tx 상세 조회", description = "tx의 id에 대한 상세 정보를 반환합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "tx 상세 조회 성공", content = @Content(schema = @Schema(implementation = TransactionRespDto.class))),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<TransactionRespDto> getTransaction(@Parameter(name = "id", description = "tx의 id") @PathVariable("id") Long transactionId) {
        return new ResponseEntity<>(
                TransactionRespDto.fromEntity(transactionService.getTransaction(transactionId)),
                HttpStatus.OK
        );
    }

    @GetMapping("{id}/refund")
    public ResponseEntity<TransactionRespDto> getRefundTransaction(@Parameter(name = "id", description = "tx의 id") @PathVariable("id") Long transactionId) {
        Transaction refundTx = transactionService.getTransaction(transactionId);
        Transaction parentTx = transactionService.getTransaction(refundTx.getParentId());
        return new ResponseEntity<>(
                TransactionRespDto.fromEntityParentTx(refundTx, parentTx),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "tx 페이지 조회 API",
            description = "페이지(index)에 해당하는 tx 개수(pageSize)의 정보를 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "tx 페이지 조회 성공", content = @Content(schema = @Schema(implementation = TransactionRespDto.class))),
                    @ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근")
            })
    @GetMapping
    public ResponseEntity<Page<TransactionRespDto>> getCountOffsetTransaction(
            @Parameter(name = "index", description = "tx 페이지 번호") @NotBlank @RequestParam(defaultValue = DEFAULT_INDEX) int index,
            @Parameter(name = "pageSize", description = "페이지 내부 tx 개수") @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize
    ) {
        return new ResponseEntity<>(
                TransactionRespDto.fromEntityPage(transactionService.getCountOffsetTransaction(pageSize, index)),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "특정 유저 tx 페이지 조회 API",
            description = "memberId 유저의 페이지(index)에 해당하는 tx 개수(pageSize)의 정보를 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "tx 페이지 조회 성공", content = @Content(schema = @Schema(implementation = TransactionRespDto.class))),
                    @ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근")
            })
    @GetMapping("/member")
    public ResponseEntity<Page<TransactionRespDto>> getMemberCountOffsetTransaction(
            @Parameter(name = "memberId", description = "유저 id") @NotBlank @RequestParam Long memberId,
            @Parameter(name = "index", description = "tx 페이지 번호") @NotBlank @RequestParam(defaultValue = DEFAULT_INDEX) int index,
            @Parameter(name = "pageSize", description = "페이지 내부 tx 개수") @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize
    ) {
        return new ResponseEntity<>(
                TransactionRespDto.fromEntityPage(transactionService.getMemberCountOffsetTransaction(memberId, pageSize, index)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransactionRespDto>> getAllTransaction() {
        return new ResponseEntity<>(
                transactionService.getAllTransaction()
                        .stream()
                        .map(TransactionRespDto::fromEntity)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionRespDto> updateTransaction(
            @PathVariable("id") Long transactionId,
            @AuthenticationPrincipal MemberDto memberDto,
            @RequestBody TransactionUpdateReqDto dto
    ) {
        return new ResponseEntity<>(
                TransactionRespDto.fromEntity(transactionService.updateTransaction(memberDto.getId(), transactionId, dto)),
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

    @GetMapping("/{id}/comments")
    public ResponseEntity<Page<CommentRespDto>> getAllCommentsByTx(
            @PathVariable Long id,
            @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(name = "index", defaultValue = DEFAULT_INDEX) int index
    ){
        Page<Comment> pagedComments = commentService.getParentsByTxId(id, index, pageSize);
        Page<CommentRespDto> pagedResult = CommentRespDto.fromEntityPage(pagedComments);
        return new ResponseEntity<>(pagedResult, HttpStatus.OK);
    }
}
