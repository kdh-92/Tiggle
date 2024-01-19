package com.side.tiggle.tigglenotification.notification

import com.fasterxml.jackson.databind.ObjectMapper
import com.side.tiggle.tigglenotification.global.common.HttpHeaders
import com.side.tiggle.tigglenotification.notification.model.NotificationDto
import io.swagger.v3.oas.annotations.Operation
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
    fun getNotifications(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<List<NotificationDto>> {
        val retValue = notificationService.getAllByMemberId(memberId)
        return ResponseEntity(retValue, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getNotification(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable(name = "id") id : Long
    ): ResponseEntity<NotificationDto> {
        val noti = notificationService.getById(memberId, id)
        return ResponseEntity(NotificationDto.fromEntity(noti), HttpStatus.OK)
    }

    @PutMapping("/{id}")
    @Operation(description = "알림을 읽음 처리 한다")
    fun readNotification(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable(name = "id") id : Long
    ): ResponseEntity<Any> {
        notificationService.readNotification(memberId, id)
        return ResponseEntity(null, HttpStatus.OK)
    }


    @PostMapping("/message")
    fun sendMessage(@RequestBody dto: NotificationDto) {
        kafkaTemplate.send("tiggle-notification", dtoToString(dto))
    }

    private fun dtoToString(dto: NotificationDto): String? =
        objectMapper.writeValueAsString(dto)
}
