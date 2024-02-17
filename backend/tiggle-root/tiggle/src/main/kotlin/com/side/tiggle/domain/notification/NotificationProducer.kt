package com.side.tiggle.domain.notification

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.side.tiggle.domain.notification.dto.NotificationProduceDto
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class NotificationProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    val objectMapper = ObjectMapper().registerKotlinModule()

    fun send(notificationProduceDto: NotificationProduceDto) {
        kafkaTemplate.send(
            "tiggle-notification",
            objectMapper.writeValueAsString(notificationProduceDto)
        )
    }
}
