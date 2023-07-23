package com.side.tiggle.domain.transaction.api;

import com.side.tiggle.domain.member.MemberDto;
import com.side.tiggle.domain.transaction.dto.TransactionDto;
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
    private final String DEFAULT_COUNT = "5";
    private final String DEFAULT_OFFSET = "0";

    @PostMapping
    public ResponseEntity<TransactionDto.Response.RespDto> createTransaction(@RequestBody TransactionDto.Request.ReqDto dto) {
        ;
        return new ResponseEntity<>(
                TransactionDto.Response.RespDto.fromEntity(transactionService.createTransaction(dto)),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "tx 상세 조회", description = "tx의 id에 대한 상세 정보를 반환합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "tx 상세 조회 성공", content = @Content(schema = @Schema(implementation = TransactionDto.Response.RespDto.class))),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto.Response.RespDto> getTransaction(@Parameter(name = "id", description = "tx의 id") @PathVariable("id") Long transactionId) {
        return new ResponseEntity<>(
                TransactionDto.Response.RespDto.fromEntity(transactionService.getTransaction(transactionId)),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "tx 페이지 조회 API",
            description = "페이지(index)에 해당하는 tx 개수(pageSize)의 정보를 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "tx 페이지 조회 성공", content = @Content(schema = @Schema(implementation = TransactionDto.Response.RespDto.class))),
                    @ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근")
            })
    @GetMapping
    public ResponseEntity<Page<TransactionDto.Response.RespDto>> getCountOffsetTransaction(
            @Parameter(name = "index", description = "tx 페이지") @NotBlank @RequestParam(defaultValue = DEFAULT_COUNT) int index,
            @Parameter(name = "pageSize", description = "페이지 개수") @RequestParam(defaultValue = DEFAULT_OFFSET) int pageSize
    ) {
        return new ResponseEntity<>(
                TransactionDto.Response.RespDto.fromEntityPage(transactionService.getCountOffsetTransaction(pageSize, index)),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "특정 유저 tx 페이지 조회 API",
            description = "memberId 유저의 페이지(index)에 해당하는 tx 개수(pageSize)의 정보를 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "tx 페이지 조회 성공", content = @Content(schema = @Schema(implementation = TransactionDto.Response.RespDto.class))),
                    @ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근")
            })
    @GetMapping("/member")
    public ResponseEntity<Page<TransactionDto.Response.RespDto>> getMemberCountOffsetTransaction(
            @Parameter(name = "memberId", description = "유저 id") @NotBlank @RequestParam Long memberId,
            @Parameter(name = "index", description = "tx 페이지") @NotBlank @RequestParam(defaultValue = DEFAULT_COUNT) int index,
            @Parameter(name = "pageSize", description = "페이지 개수") @RequestParam(defaultValue = DEFAULT_OFFSET) int pageSize
    ) {
        return new ResponseEntity<>(
                TransactionDto.Response.RespDto.fromEntityPage(transactionService.getMemberCountOffsetTransaction(memberId, pageSize, index)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransactionDto.Response.RespDto>> getAllTransaction() {
        return new ResponseEntity<>(
                transactionService.getAllTransaction()
                        .stream()
                        .map(TransactionDto.Response.RespDto::fromEntity)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto.Response.RespDto> updateTransaction(
            @PathVariable("id") Long transactionId,
            @AuthenticationPrincipal MemberDto memberDto,
            @RequestBody TransactionDto.Request.ReqDto dto
    ) {
        return new ResponseEntity<>(
                TransactionDto.Response.RespDto.fromEntity(transactionService.updateTransaction(memberDto.getId(), transactionId, dto)),
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
