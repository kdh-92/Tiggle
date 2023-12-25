package com.side.tiggle.domain.transaction.api;

import com.side.tiggle.domain.asset.service.AssetService;
import com.side.tiggle.domain.category.service.CategoryService;
import com.side.tiggle.domain.comment.dto.resp.CommentRespDto;
import com.side.tiggle.domain.comment.service.CommentService;
import com.side.tiggle.domain.reaction.model.ReactionType;
import com.side.tiggle.domain.reaction.service.ReactionService;
import com.side.tiggle.domain.transaction.dto.req.TransactionUpdateReqDto;
import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.transaction.dto.TransactionDto;
import com.side.tiggle.domain.transaction.dto.resp.TransactionRespDto;
import com.side.tiggle.domain.transaction.dto.resp.TransactionUpdateRespDto;
import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.domain.transaction.service.TransactionService;
import com.side.tiggle.global.common.constants.HttpHeaders;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionApiController {

    private final TransactionService transactionService;
    private final AssetService assetService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final ReactionService reactionService;
    private final String DEFAULT_INDEX = "0";
    private final String DEFAULT_PAGE_SIZE = "5";

    // TODO: Request Header 방식으로 변경한다
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionRespDto> createTransaction(
            @RequestPart TransactionDto dto,
            @RequestPart("multipartFile") MultipartFile file
    ) throws IOException {
        Transaction tx = transactionService.createTransaction(dto, file);
        Transaction parentTx = (tx.getParentId() != null) ? transactionService.getTransaction(tx.getParentId()) : null;

        return new ResponseEntity<>(
                TransactionRespDto.fromEntityDetailTx(
                        tx,
                        parentTx
                ), HttpStatus.CREATED
        );
    }

    @Operation(summary = "tx 상세 조회", description = "tx의 id에 대한 상세 정보를 반환합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "tx 상세 조회 성공", content = @Content(schema = @Schema(implementation = TransactionRespDto.class))),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<TransactionRespDto> getTransaction(@Parameter(name = "id", description = "tx의 id") @PathVariable("id") Long transactionId) {
        Transaction tx = transactionService.getTransaction(transactionId);
        Transaction parentTx = (tx.getParentId() != null) ? transactionService.getTransaction(tx.getParentId()) : null;
        return new ResponseEntity<>(
                TransactionRespDto.fromEntityDetailTx(
                        tx,
                        parentTx
                ), HttpStatus.OK
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
        Page<Transaction> txPage = transactionService.getCountOffsetTransaction(pageSize, index);
        List<TransactionRespDto> dtoList = txPage.getContent()
                .stream()
                .map(tx -> {
                    long txId = tx.getId();
                    int txUpCount = reactionService.getReactionCount(txId, ReactionType.DOWN);
                    int txDownCount = reactionService.getReactionCount(txId, ReactionType.UP);
                    int txCommentCount = commentService.getParentCount(txId);
                    return TransactionRespDto.fromEntityWithCount(tx, txUpCount, txDownCount, txCommentCount);
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(
                TransactionRespDto.fromEntityPage(txPage, dtoList),
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
        Page<Transaction> txPage = transactionService.getMemberCountOffsetTransaction(memberId, pageSize, index);
        List<TransactionRespDto> dtoList = txPage.getContent()
                .stream()
                .map(tx -> {
                    long txId = tx.getId();
                    int txUpCount = reactionService.getReactionCount(txId, ReactionType.DOWN);
                    int txDownCount = reactionService.getReactionCount(txId, ReactionType.UP);
                    int txCommentCount = commentService.getParentCount(txId);
                    return TransactionRespDto.fromEntityWithCount(tx, txUpCount, txDownCount, txCommentCount);
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(
                TransactionRespDto.fromEntityPage(txPage, dtoList),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransactionRespDto>> getAllTransaction() {
        return new ResponseEntity<>(
                transactionService.getAllUndeletedTransaction()
                        .stream()
                        .map(tx -> {
                            long txId = tx.getId();
                            int txUpCount = reactionService.getReactionCount(txId, ReactionType.DOWN);
                            int txDownCount = reactionService.getReactionCount(txId, ReactionType.UP);
                            int txCommentCount = commentService.getParentCount(txId);
                            return TransactionRespDto.fromEntityWithCount(tx, txUpCount, txDownCount, txCommentCount);
                        })
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(description = "트랜잭션 수정", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<TransactionRespDto> updateTransaction(
            @Parameter(hidden = true)
            @RequestHeader(name = HttpHeaders.MEMBER_ID) long memberId,
            @PathVariable("id") Long transactionId,
            @RequestBody TransactionUpdateReqDto dto
    ) {
        Transaction tx = transactionService.updateTransaction(memberId, transactionId, dto);
        return new ResponseEntity<>(
                TransactionUpdateRespDto.fromEntity(
                        tx
                ), HttpStatus.OK
        );
    }
    @DeleteMapping("/{id}")
    @Operation(description = "트랜잭션 삭제", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<Void> deleteTransaction(
            @Parameter(hidden = true)
            @RequestHeader(name = HttpHeaders.MEMBER_ID) long memberId,
            @PathVariable("id") Long transactionId
    ) {
        transactionService.deleteTransaction(memberId, transactionId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<Page<CommentRespDto>> getAllCommentsByTx(
            @PathVariable Long id,
            @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(name = "index", defaultValue = DEFAULT_INDEX) int index
    ) {
        Page<Comment> pagedComments = commentService.getParentsByTxId(id, index, pageSize);
        Page<CommentRespDto> pagedResult = CommentRespDto.fromEntityPage(pagedComments, commentService);
        return new ResponseEntity<>(pagedResult, HttpStatus.OK);
    }
}
