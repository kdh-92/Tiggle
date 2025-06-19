package com.side.tiggle.domain.transaction.dto.req

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PastOrPresent
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class TransactionUpdateReqDto(
    @field:Positive(message = "금액은 양수여야 합니다")
    val amount: Int,

    @field:PastOrPresent(message = "미래 날짜는 입력할 수 없습니다")
    val date: LocalDate,

    @field:NotBlank(message = "내용은 필수입니다")
    @field:Size(max = 255, message = "내용은 255자 이하여야 합니다")
    val content: String,

    @field:NotBlank(message = "사유는 필수입니다")
    @field:Size(max = 255, message = "사유는 255자 이하여야 합니다")
    val reason: String,

    @field:Positive(message = "자산 ID는 양수여야 합니다")
    val assetId: Long,

    @field:Positive(message = "카테고리 ID는 양수여야 합니다")
    val categoryId: Long,

    @field:Size(max = 10, message = "태그는 최대 10개까지 가능합니다")
    val tagNames: List<String>
)