package com.side.tiggle.domain.achievement.api

import com.side.tiggle.domain.achievement.dto.resp.AchievementRespDto
import com.side.tiggle.domain.achievement.service.AchievementService
import com.side.tiggle.global.common.ApiResponse
import com.side.tiggle.global.common.constants.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/v1/achievements")
class AchievementApiController(
    private val achievementService: AchievementService
) {

    @GetMapping
    fun getAllAchievements(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<ApiResponse<List<AchievementRespDto>>> {
        val achievements = achievementService.getAllAchievements(memberId)
        return ResponseEntity.ok(ApiResponse.success(achievements))
    }

    @GetMapping("/recent")
    fun getRecentAchievements(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @RequestParam(defaultValue = "5") limit: Int
    ): ResponseEntity<ApiResponse<List<AchievementRespDto>>> {
        val achievements = achievementService.getRecentAchievements(memberId, limit)
        return ResponseEntity.ok(ApiResponse.success(achievements))
    }
}
