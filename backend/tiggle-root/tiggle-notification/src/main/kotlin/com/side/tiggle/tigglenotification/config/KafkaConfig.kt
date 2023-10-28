package com.side.tiggle.tigglenotification.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.side.tiggle.tigglenotification.notification.NotificationService
import com.side.tiggle.tigglenotification.notification.model.Notification
import com.side.tiggle.tigglenotification.notification.model.NotificationDto
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.KafkaListener
import java.lang.Exception


@Configuration
class KafkaConfig(
        private val notificationService: NotificationService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(topics = ["tiggle-notification"])
    fun listen(record: ConsumerRecord<String, String>) {
        try {
            logger.info(record.value())
            val message = jacksonMapper.readValue(record.value(), NotificationDto::class.java)
            notificationService.save(message)
        } catch (e: Exception) {
            // TODO: 어떤 Exception인지에 따라 분기할 것
            logger.error(e.message, e)
        }

    }
}