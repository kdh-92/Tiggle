package com.side.tiggle.domain.notification.api

import com.side.tiggle.domain.notification.dto.resp.NotificationRespDto
import com.side.tiggle.domain.notification.service.NotificationService
import com.side.tiggle.global.common.constants.HttpHeaders
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
        @RequestHeader(name = "member-id") overrideMemberId: Long?,
    ): ResponseEntity<List<NotificationRespDto>>{
        val result = notificationService.getAllByMemberId(overrideMemberId ?: memberId)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun readNotification(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable(name = "id") id: Long
    ): ResponseEntity<Nothing> {
        notificationService.readNotificationById(memberId, id)
        return ResponseEntity(null, HttpStatus.OK)
    }

}