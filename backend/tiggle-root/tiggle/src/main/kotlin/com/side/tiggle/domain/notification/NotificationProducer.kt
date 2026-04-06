package com.side.tiggle.domain.notification

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.side.tiggle.domain.notification.dto.NotificationProduceDto
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class NotificationProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    val objectMapper = ObjectMapper().registerKotlinModule()
    val logger = LoggerFactory.getLogger(this::class.java)

    @Async
    fun send(notificationProduceDto: NotificationProduceDto) {
        try {
            kafkaTemplate.send(
                "tiggle-notification",
                objectMapper.writeValueAsString(notificationProduceDto)
            )
        } catch (e: Exception) {
            logger.error(e.message, e)
        }
    }
}
