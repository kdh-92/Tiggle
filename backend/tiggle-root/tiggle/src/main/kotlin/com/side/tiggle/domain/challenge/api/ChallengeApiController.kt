package com.side.tiggle.domain.challenge.api

import com.side.tiggle.domain.challenge.dto.req.ChallengeCreateReqDto
import com.side.tiggle.domain.challenge.dto.resp.ChallengeDetailRespDto
import com.side.tiggle.domain.challenge.dto.resp.ChallengeRespDto
import com.side.tiggle.domain.challenge.service.ChallengeService
import com.side.tiggle.global.common.ApiResponse
import com.side.tiggle.global.common.constants.HttpHeaders
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/v1/challenges")
class ChallengeApiController(
    private val challengeService: ChallengeService
) {

    /**
     * 챌린지 생성
     */
    @PostMapping
    fun createChallenge(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @Valid @RequestBody dto: ChallengeCreateReqDto
    ): ResponseEntity<ApiResponse<ChallengeRespDto>> {
        val result = challengeService.createChallenge(memberId, dto)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(result))
    }

    /**
     * 현재 활성 챌린지 조회
     */
    @GetMapping("/active")
    fun getActiveChallenge(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<ApiResponse<ChallengeRespDto?>> {
        val result = challengeService.getActiveChallenge(memberId)
        return ResponseEntity.ok(ApiResponse.success(result))
    }

    /**
     * 챌린지 상세 조회 (일일 로그 포함)
     */
    @GetMapping("/{id}")
    fun getChallengeDetail(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<ChallengeDetailRespDto>> {
        val result = challengeService.getChallengeDetail(memberId, id)
        return ResponseEntity.ok(ApiResponse.success(result))
    }

    /**
     * 챌린지 이력 조회 (페이징)
     */
    @GetMapping("/history")
    fun getChallengeHistory(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<Page<ChallengeRespDto>>> {
        val result = challengeService.getChallengeHistory(memberId, page, size)
        return ResponseEntity.ok(ApiResponse.success(result))
    }

    /**
     * 챌린지 취소
     */
    @DeleteMapping("/{id}")
    fun cancelChallenge(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<Void>> {
        challengeService.cancelChallenge(memberId, id)
        return ResponseEntity.ok(ApiResponse.success(null))
    }
}
