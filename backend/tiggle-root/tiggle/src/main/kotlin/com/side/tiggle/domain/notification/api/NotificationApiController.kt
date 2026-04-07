package com.side.tiggle.domain.notification.api

import com.side.tiggle.domain.notification.dto.resp.NotificationRespDto
import com.side.tiggle.domain.notification.service.NotificationService
import com.side.tiggle.global.common.ApiResponse
import com.side.tiggle.global.common.constants.HttpHeaders
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.constraints.Min
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/api/v1/notification")
class NotificationApiController(
    private val notificationService: NotificationService
) {

    @GetMapping
    @Operation(summary = "모든 알림 조회", security = [SecurityRequirement(name = "bearer-key")])
    fun getAllByMember(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
    ): ResponseEntity<ApiResponse<List<NotificationRespDto>>> {
        val result = notificationService.getAllByMemberId(memberId)
        return ResponseEntity
            .ok(ApiResponse.success(result))
    }

    @PutMapping("/{id}")
    @Operation(summary = "알림 읽음 처리", security = [SecurityRequirement(name = "bearer-key")])
    fun readNotification(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable(name = "id") @Min(1) id: Long
    ): ResponseEntity<ApiResponse<Nothing>> {
        notificationService.readNotificationById(memberId, id)
        return ResponseEntity
            .ok(ApiResponse.success(null))
    }

    @PutMapping("/read-all")
    @Operation(summary = "모든 알림 읽음 처리", security = [SecurityRequirement(name = "bearer-key")])
    fun readAllNotifications(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
    ): ResponseEntity<ApiResponse<Nothing>> {
        notificationService.readAllNotifications(memberId)
        return ResponseEntity
            .ok(ApiResponse.success(null, message = "모든 알림을 읽음 처리했습니다."))
    }

    @GetMapping("/unread-count")
    @Operation(summary = "미읽음 알림 수 조회", security = [SecurityRequirement(name = "bearer-key")])
    fun getUnreadCount(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
    ): ResponseEntity<ApiResponse<Long>> {
        val count = notificationService.getUnreadCount(memberId)
        return ResponseEntity
            .ok(ApiResponse.success(count))
    }
}
