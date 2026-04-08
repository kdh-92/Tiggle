package com.side.tiggle.domain.statistics.dto.resp

data class MonthlySummaryRespDto(
    val year: Int,
    val month: Int,
    val totalOutcome: Int,
    val totalIncome: Int,
    val totalRefund: Int,
    val transactionCount: Int,
    val categoryBreakdown: List<CategoryBreakdownDto>
)

data class CategoryBreakdownDto(
    val categoryId: Long,
    val categoryName: String,
    val amount: Int,
    val ratio: Double
)
