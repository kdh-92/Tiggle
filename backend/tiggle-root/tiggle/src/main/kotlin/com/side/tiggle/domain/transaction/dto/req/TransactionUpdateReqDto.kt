package com.side.tiggle.domain.transaction.dto.req

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class TransactionUpdateReqDto(
    @field:NotNull(message = "금액은 필수입니다")
    @field:PositiveOrZero(message = "금액은 0원 이상이어야 합니다")
    val amount: Int,

    @field:NotNull(message = "날짜는 필수입니다")
    val date: LocalDate,

    @field:NotBlank(message = "내용은 필수입니다")
    @field:Size(max = 20, message = "내용은 20자 이하여야 합니다")
    val content: String,

    @field:NotBlank(message = "사유는 필수입니다")
    @field:Size(max = 300, message = "사유는 300자 이하여야 합니다")
    val reason: String,

    @field:NotNull(message = "카테고리 ID는 필수입니다")
    @field:Min(value = 1, message = "올바른 카테고리 ID를 입력해주세요")
    val categoryId: Long,

    @field:Size(max = 50, message = "태그는 최대 50개까지 가능합니다")
    val tagNames: List<String>?
)
