package com.side.tiggle.tigglenotification.notification

import com.fasterxml.jackson.databind.ObjectMapper
import com.side.tiggle.tigglenotification.global.common.HttpHeaders
import com.side.tiggle.tigglenotification.notification.model.NotificationDto
import jakarta.validation.constraints.Min
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/v1/notification")
class NotificationController(
    private val notificationService: NotificationService,
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {
    @GetMapping
    fun getNotification(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) @Min(1) memberId: Long
    ): ResponseEntity<List<NotificationDto>> {
        val retValue = notificationService.getAllByMemberId(memberId)
        return ResponseEntity(retValue, HttpStatus.OK)
    }

    private fun dtoToString(dto: NotificationDto): String? =
        objectMapper.writeValueAsString(dto)
}
