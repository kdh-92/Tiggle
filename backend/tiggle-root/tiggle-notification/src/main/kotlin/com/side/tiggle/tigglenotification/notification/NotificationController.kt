package com.side.tiggle.tigglenotification.notification

import com.side.tiggle.tigglenotification.global.common.HttpHeaders
import com.side.tiggle.tigglenotification.notification.model.NotificationDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController(value = "/api/v1/notification")
class NotificationController(
    private val notificationService: NotificationService
) {
    @GetMapping
    fun getNotification(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<List<NotificationDto>> {
        val retValue = notificationService.getAllByMemberId(memberId)
        return ResponseEntity(retValue, HttpStatus.OK)
    }
}