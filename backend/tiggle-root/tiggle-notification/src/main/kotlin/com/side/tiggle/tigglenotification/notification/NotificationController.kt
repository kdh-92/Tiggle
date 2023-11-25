package com.side.tiggle.tigglenotification.notification

import com.fasterxml.jackson.databind.ObjectMapper
import com.side.tiggle.tigglenotification.global.common.HttpHeaders
import com.side.tiggle.tigglenotification.notification.model.NotificationDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/notification")
class NotificationController(
    private val notificationService: NotificationService,
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {
    @GetMapping
    fun getNotification(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<List<NotificationDto>> {
        val retValue = notificationService.getAllByMemberId(memberId)
        return ResponseEntity(retValue, HttpStatus.OK)
    }

    @PostMapping("/message")
    fun sendMessage(@RequestBody dto: NotificationDto) {
        kafkaTemplate.send("tiggle-notification", dtoToString(dto))
    }

    private fun dtoToString(dto: NotificationDto): String? =
        objectMapper.writeValueAsString(dto)
}
