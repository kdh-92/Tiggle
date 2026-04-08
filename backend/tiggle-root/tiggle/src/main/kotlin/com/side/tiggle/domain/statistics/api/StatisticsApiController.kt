package com.side.tiggle.domain.statistics.api

import com.side.tiggle.domain.statistics.dto.resp.MonthlySummaryRespDto
import com.side.tiggle.domain.statistics.dto.resp.WeeklyComparisonRespDto
import com.side.tiggle.domain.statistics.service.StatisticsService
import com.side.tiggle.global.common.ApiResponse
import com.side.tiggle.global.common.constants.HttpHeaders
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.constraints.Min
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/v1/statistics")
class StatisticsApiController(
    private val statisticsService: StatisticsService
) {

    @Operation(
        summary = "주간 지출 비교",
        description = "현재 주와 이전 주의 지출을 비교하고 이상 감지 결과를 반환합니다.",
        security = [SecurityRequirement(name = "bearer-key")]
    )
    @GetMapping("/weekly-comparison")
    fun getWeeklyComparison(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @Parameter(name = "weekOffset", description = "조회할 주 오프셋 (0=현재 주, 1=지난 주)")
        @RequestParam(defaultValue = "0") @Min(0) weekOffset: Int
    ): ResponseEntity<ApiResponse<WeeklyComparisonRespDto>> {
        val result = statisticsService.getWeeklyComparison(memberId, weekOffset)
        return ResponseEntity.ok(ApiResponse.success(result))
    }

    @Operation(
        summary = "월간 요약",
        description = "이번 달 총 지출/수입/환불 및 카테고리별 분석을 반환합니다.",
        security = [SecurityRequirement(name = "bearer-key")]
    )
    @GetMapping("/monthly-summary")
    fun getMonthlySummary(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<ApiResponse<MonthlySummaryRespDto>> {
        val result = statisticsService.getMonthlySummary(memberId)
        return ResponseEntity.ok(ApiResponse.success(result))
    }
}
