package com.side.tiggle.domain.transaction.api

import com.side.tiggle.domain.comment.dto.resp.CommentRespDto
import com.side.tiggle.domain.comment.dto.resp.CommentRespDto.Companion.fromEntityPage
import com.side.tiggle.domain.comment.service.CommentService
import com.side.tiggle.domain.reaction.model.ReactionType
import com.side.tiggle.domain.reaction.service.ReactionService
import com.side.tiggle.domain.transaction.dto.TransactionDto
import com.side.tiggle.domain.transaction.dto.req.TransactionUpdateReqDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionRespDto
import com.side.tiggle.domain.transaction.model.Transaction
import com.side.tiggle.domain.transaction.service.TransactionService
import com.side.tiggle.global.common.constants.HttpHeaders
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.data.domain.Page
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/transaction")
class TransactionApiController(
    private val transactionService: TransactionService,
    private val commentService: CommentService,
    private val reactionService: ReactionService
) {

    // TODO: Request Header 방식으로 변경한다
    @Operation(description = "tx 생성", security = [SecurityRequirement(name = "bearer-key")])
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createTransaction(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @RequestPart dto: TransactionDto,
        @RequestPart(value = "multipartFile", required = false) file: MultipartFile?
    ): ResponseEntity<TransactionRespDto> {
        val tx = transactionService.createTransaction(memberId, dto, file)
        return ResponseEntity(
            TransactionRespDto.fromEntityDetailTx(tx), HttpStatus.CREATED
        )
    }

    @Operation(summary = "tx 상세 조회", description = "tx의 id에 대한 상세 정보를 반환합니다.", responses = [ApiResponse(responseCode = "200", description = "tx 상세 조회 성공"), ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근")])
    @GetMapping("/{id}")
    fun getTransaction(
        @Parameter(name = "id", description = "tx의 id")
        @PathVariable("id") transactionId: Long
    ): ResponseEntity<TransactionRespDto> {
        val tx = transactionService.getTransaction(transactionId)
        return ResponseEntity(
            TransactionRespDto.fromEntityDetailTx(tx), HttpStatus.OK
        )
    }

    @Operation(summary = "tx 페이지 조회 API", description = "페이지(index)에 해당하는 tx 개수(pageSize)의 정보를 반환합니다.", responses = [ApiResponse(responseCode = "200", description = "tx 페이지 조회 성공"), ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근")])
    @GetMapping
    fun getCountOffsetTransaction(
        @Parameter(name = "index", description = "tx 페이지 번호")
        @RequestParam(defaultValue = DEFAULT_INDEX) index: Int,
        @Parameter(name = "pageSize", description = "페이지 내부 tx 개수")
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) pageSize: Int
    ): ResponseEntity<Page<TransactionRespDto>> {
        val txPage = transactionService.getCountOffsetTransaction(pageSize, index)
        val dtoList = txPage.content.map { mapTxRespDto(it) }
        return ResponseEntity(
            TransactionRespDto.fromEntityPage(txPage, dtoList),
            HttpStatus.OK
        )
    }

    @Operation(summary = "특정 유저 tx 페이지 조회 API", description = "memberId 유저의 페이지(index)에 해당하는 tx 개수(pageSize)의 정보를 반환합니다.", responses = [ApiResponse(responseCode = "200", description = "tx 페이지 조회 성공"), ApiResponse(responseCode = "400", description = "존재하지 않는 리소스 접근")])
    @GetMapping("/member")
    fun getMemberCountOffsetTransaction(
        @Parameter(name = "memberId", description = "유저 id")
        @RequestParam memberId: Long,
        @Parameter(name = "index", description = "tx 페이지 번호")
        @RequestParam(defaultValue = DEFAULT_INDEX) index: Int,
        @Parameter(name = "pageSize", description = "페이지 내부 tx 개수")
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) pageSize: Int,
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
    ): ResponseEntity<Page<TransactionRespDto>> {
        val txPage = transactionService.getMemberCountOffsetTransaction(memberId, pageSize, index)
        val dtoList = txPage.content.filter {

            // TODO : 필터링을 repository 레벨에서 수행한다?
            val startCheck = start == null || start.isBefore(it.date)
            val endCheck = end == null || end.isAfter(it.date)
            val categoryCheck = category.isNullOrEmpty() || category.contains(it.category.id!!)

            val currentTags = it.tagNames
            val tagNamesCheck = if (currentTags != null && tagNames.isNullOrEmpty().not()) {
                currentTags.stream().anyMatch { o: String? -> tagNames!!.contains(o) }
            } else {
                true
            }
            startCheck && endCheck && categoryCheck && tagNamesCheck
        }.map {mapTxRespDto(it) }
        return ResponseEntity(
            TransactionRespDto.fromEntityPage(txPage, dtoList),
            HttpStatus.OK
        )
    }

    @GetMapping("/all")
    fun getAllTransaction(): ResponseEntity<List<TransactionRespDto>> {
        return ResponseEntity<List<TransactionRespDto>>(
            transactionService.getAllUndeletedTransaction()
                .map { mapTxRespDto(it) },
            HttpStatus.OK)
    }

    @PutMapping("/{id}")
    @Operation(description = "트랜잭션 수정", security = [SecurityRequirement(name = "bearer-key")])
    fun updateTransaction(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable("id") transactionId: Long,
        @RequestBody dto: TransactionUpdateReqDto
    ): ResponseEntity<TransactionRespDto> {
        val tx = transactionService.updateTransaction(memberId, transactionId, dto)
        return ResponseEntity<TransactionRespDto>(
            TransactionRespDto.fromEntity(tx), HttpStatus.OK
        )
    }

    @DeleteMapping("/{id}")
    @Operation(description = "트랜잭션 삭제", security = [SecurityRequirement(name = "bearer-key")])
    fun deleteTransaction(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable("id") transactionId: Long
    ): ResponseEntity<Nothing> {
        transactionService.deleteTransaction(memberId, transactionId)
        return ResponseEntity(null, HttpStatus.NO_CONTENT)
    }

    @GetMapping("/{id}/comments")
    fun getAllCommentsByTx(
        @PathVariable id: Long,
        @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) pageSize: Int,
        @RequestParam(name = "index", defaultValue = DEFAULT_INDEX) index: Int
    ): ResponseEntity<Page<CommentRespDto>> {
        val pagedComments = commentService.getParentsByTxId(id, index, pageSize)
        val pagedResult = fromEntityPage(pagedComments, commentService)
        return ResponseEntity(pagedResult, HttpStatus.OK)
    }

    private fun mapTxRespDto(tx: Transaction): TransactionRespDto {
        val txId = tx.id!!
        val txDownCount = reactionService.getReactionCount(txId, ReactionType.DOWN)
        val txUpCount = reactionService.getReactionCount(txId, ReactionType.UP)
        val txCommentCount =commentService.getParentCount(txId)
        return TransactionRespDto.fromEntityWithCount(
            tx = tx,
            txUpCount = txUpCount,
            txDownCount = txDownCount,
            txCommentCount = txCommentCount
        )
    }

    companion object {
        private const val DEFAULT_INDEX = "0"
        private const val DEFAULT_PAGE_SIZE = "5"
    }
}