package com.side.tiggle.domain.transaction.api

import com.side.tiggle.domain.transaction.dto.resp.TransactionRespDto
import com.side.tiggle.domain.comment.dto.resp.CommentPageRespDto
import com.side.tiggle.domain.comment.service.CommentService
import com.side.tiggle.domain.transaction.dto.req.TransactionCreateReqDto
import com.side.tiggle.domain.transaction.dto.req.TransactionUpdateReqDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionListRespDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionPageRespDto
import com.side.tiggle.domain.transaction.service.TransactionService
import com.side.tiggle.global.common.constants.HttpHeaders
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import io.swagger.v3.oas.annotations.responses.ApiResponse as SwaggerApiResponse
import com.side.tiggle.global.common.ApiResponse

@Validated
@RestController
@RequestMapping("/api/v1/transaction")
class TransactionApiController(
    private val transactionService: TransactionService,
    private val commentService: CommentService,
) {
    // TODO: Request Header 방식으로 변경한다
    @Operation(description = "tx 생성", security = [SecurityRequirement(name = "bearer-key")])
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createTransaction(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @RequestPart @Valid dto: TransactionCreateReqDto,
        @RequestPart(value = "files", required = true) files: List<MultipartFile>
    ): ResponseEntity<ApiResponse<Nothing>> {
        transactionService.createTransaction(memberId, dto, files)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(null, message = "거래가 생성되었습니다."))
    }

    @Operation(summary = "tx 상세 조회", description = "tx의 id에 대한 상세 정보를 반환합니다.", responses = [SwaggerApiResponse(responseCode = "200", description = "tx 상세 조회 성공"), SwaggerApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근")])
    @GetMapping("/{id}")
    fun getTransaction(
        @Parameter(name = "id", description = "tx의 id")
        @PathVariable("id") @Min(1) transactionId: Long
    ): ResponseEntity<ApiResponse<TransactionRespDto>> {
        val tx = transactionService.getTransactionDetail(transactionId)
        return ResponseEntity
            .ok(ApiResponse.success(tx))
    }

    @Operation(summary = "tx 페이지 조회 API", description = "페이지(index)에 해당하는 tx 개수(pageSize)의 정보를 반환합니다.", responses = [SwaggerApiResponse(responseCode = "200", description = "tx 페이지 조회 성공"), SwaggerApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근")])
    @GetMapping
    fun getCountOffsetTransaction(
        @Parameter(name = "index", description = "tx 페이지 번호")
        @RequestParam(defaultValue = DEFAULT_INDEX) @Min(0) index: Int,
        @Parameter(name = "pageSize", description = "페이지 내부 tx 개수")
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Min(1) @Max(100) pageSize: Int
    ): ResponseEntity<ApiResponse<TransactionPageRespDto>> {
        val txPage = transactionService.getCountOffsetTransaction(pageSize, index)
        return ResponseEntity
            .ok(ApiResponse.success(txPage))
    }

    @Operation(summary = "특정 유저 tx 페이지 조회 API", description = "memberId 유저의 페이지(index)에 해당하는 tx 개수(pageSize)의 정보를 반환합니다.", responses = [SwaggerApiResponse(responseCode = "200", description = "tx 페이지 조회 성공"), SwaggerApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근")])
    @GetMapping("/member")
    fun getMemberCountOffsetTransaction(
        @Parameter(name = "memberId", description = "유저 id")
        @RequestParam @Min(1) memberId: Long,
        @Parameter(name = "index", description = "tx 페이지 번호")
        @RequestParam(defaultValue = DEFAULT_INDEX) @Min(0) index: Int,
        @Parameter(name = "pageSize", description = "페이지 내부 tx 개수")
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Min(1) @Max(100) pageSize: Int,
        // 이하 필터링 항목
        @Parameter(description = "(필터링) 트랜잭션 일자 기준 시작")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @RequestParam(required = false) start: LocalDate?,
        @Parameter(description = "(필터링) 트랜잭션 일자 기준 끝")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @RequestParam(required = false) end: LocalDate?,
        @Parameter(description = "(필터링) 카테고리 종류 (복수)")
        @RequestParam(required = false) category: List<Long>?,
        @Parameter(description = "(필터링) 자산 종류 (복수)")
        @RequestParam(required = false) asset: List<Long>?,
        @Parameter(description = "(필터링) 태그 이름 (복수)")
        @RequestParam(required = false) tagNames: List<String>?
    ): ResponseEntity<ApiResponse<TransactionPageRespDto>> {
        val txPage = transactionService.getMemberCountOffsetTransaction(
            memberId = memberId,
            count = pageSize,
            offset = index,
            startDate = start,
            endDate = end,
            categoryIds = category,
            tagNames = tagNames
        )

        return ResponseEntity
            .ok(ApiResponse.success(txPage))
    }

    @PutMapping("/{id}")
    @Operation(description = "트랜잭션 수정", security = [SecurityRequirement(name = "bearer-key")])
    fun updateTransaction(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable("id") @Min(1) transactionId: Long,
        @RequestBody @Valid dto: TransactionUpdateReqDto
    ): ResponseEntity<ApiResponse<Nothing>> {
        transactionService.updateTransaction(memberId, transactionId, dto)

        return ResponseEntity
            .ok(ApiResponse.success(null, message = "거래가 수정되었습니다."))
    }

    @DeleteMapping("/{id}")
    @Operation(description = "트랜잭션 삭제", security = [SecurityRequirement(name = "bearer-key")])
    fun deleteTransaction(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable("id") @Min(1) transactionId: Long
    ): ResponseEntity<ApiResponse<Nothing>> {
        transactionService.deleteTransaction(memberId, transactionId)
        return ResponseEntity
            .ok(ApiResponse.success(null))
    }

    @GetMapping("/{id}/comments")
    fun getAllCommentsByTx(
        @PathVariable @Min(1) id: Long,
        @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) @Min(1) @Max(100) pageSize: Int,
        @RequestParam(name = "index", defaultValue = DEFAULT_INDEX) @Min(0) index: Int
    ): ResponseEntity<ApiResponse<CommentPageRespDto>> {
        val pagedCommentsRespDto = commentService.getParentsByTxId(id, index, pageSize)
        return ResponseEntity
            .ok(ApiResponse.success(pagedCommentsRespDto))
    }

    companion object {
        private const val DEFAULT_INDEX = "0"
        private const val DEFAULT_PAGE_SIZE = "5"
    }

    @PostMapping("/{id}/photos", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(description = "거래 사진 추가", security = [SecurityRequirement(name = "bearer-key")])
    fun addTransactionPhotos(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable("id") @Min(1) transactionId: Long,
        @RequestPart(value = "files", required = true) files: List<MultipartFile>
    ): ResponseEntity<ApiResponse<Nothing>> {
        transactionService.addTransactionPhotos(memberId, transactionId, files)
        return ResponseEntity
            .ok(ApiResponse.success(null, message = "사진이 추가되었습니다."))
    }

    @DeleteMapping("/{id}/photos/{photoIndex}")
    @Operation(description = "거래 사진 삭제", security = [SecurityRequirement(name = "bearer-key")])
    fun deleteTransactionPhoto(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable("id") @Min(1) transactionId: Long,
        @PathVariable("photoIndex") @Min(0) photoIndex: Int
    ): ResponseEntity<ApiResponse<Nothing>> {
        transactionService.deleteTransactionPhoto(memberId, transactionId, photoIndex)
        return ResponseEntity
            .ok(ApiResponse.success(null, message = "사진이 삭제되었습니다."))
    }
}
