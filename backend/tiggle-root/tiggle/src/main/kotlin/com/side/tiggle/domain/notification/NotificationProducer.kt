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
        // TODO: 현재 카프카가 죽은 경우 hang 이 걸리는 문제가 있어서 임시로 비활성화 합니다.
//        try {
//            kafkaTemplate.send(
//                "tiggle-notification",
//                objectMapper.writeValueAsString(notificationProduceDto)
//            )
//        } catch (e: Exception) {
//            logger.error(e.message, e)
//        }
    }
}
