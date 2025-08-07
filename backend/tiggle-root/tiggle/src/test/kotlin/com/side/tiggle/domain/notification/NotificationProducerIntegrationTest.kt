package com.side.tiggle.domain.notification

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.side.tiggle.domain.notification.dto.NotificationProduceDto
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Duration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.util.Properties
import java.util.UUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebMvc
@TestPropertySource(properties = [
    "spring.kafka.bootstrap-servers=localhost:9092"
])
@Sql("/sql/test-data.sql")
@Transactional
@Rollback
class NotificationProducerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val objectMapper = ObjectMapper().registerKotlinModule()

    @Test
    fun `댓글 작성 시 Kafka에 알림 메시지가 발행되는지 테스트`() {
        // Given: 테스트용 Kafka Consumer 준비
        val consumer = createTestConsumer()
        consumer.subscribe(listOf("tiggle-notification"))

        // When: 댓글 작성 API 호출
        mockMvc.perform(
            post("/api/v1/comments")
                .header("x-member-id", "10")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "txId": 27,
                        "content": "통합테스트용 댓글입니다",
                        "parentId": null
                    }
                """.trimIndent())
        ).andExpect(status().isCreated)

        // Then: 카프카 Topic에서 메시지 확인
        val records: ConsumerRecords<String, String> = KafkaTestUtils.getRecords(
            consumer,
            Duration.ofSeconds(10)
        )

        assertThat(records.count()).isEqualTo(1)

        val messageRecord = records.iterator().next()
        val messageJson = messageRecord.value()

        // 메시지 내용 검증
        val notification = objectMapper.readValue(messageJson, NotificationProduceDto::class.java)

        assertThat(notification.receiverId).isNotNull()
        assertThat(notification.senderId).isEqualTo(10L)
        assertThat(notification.content).isEqualTo("통합테스트용 댓글입니다")
        assertThat(notification.txId).isEqualTo(27L)
        assertThat(notification.type).isIn(
            NotificationProduceDto.Type.COMMENT,
            NotificationProduceDto.Type.REPLY
        )

        consumer.close()
    }

    @Test
    fun `대댓글 작성 시 Kafka에 REPLY 타입 메시지가 발행되는지 테스트`() {
        // Given: 테스트용 카프카 컨슈머 준비
        val consumer = createTestConsumer()
        consumer.subscribe(listOf("tiggle-notification"))

        // When: 대댓글 작성 API 호출 (parentId 있음)
        mockMvc.perform(
            post("/api/v1/comments")
                .header("x-member-id", "10")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "txId": 27,
                        "content": "대댓글 테스트입니다",
                        "parentId": 1
                    }
                """.trimIndent())
        ).andExpect(status().isCreated)

        // Then: 카프카에서 REPLY 타입 메시지 확인
        val records = KafkaTestUtils.getRecords(consumer, Duration.ofSeconds(10))

        assertThat(records.count()).isEqualTo(1)

        val messageJson = records.iterator().next().value()
        val notification = objectMapper.readValue(messageJson, NotificationProduceDto::class.java)

        assertThat(notification.type).isEqualTo(NotificationProduceDto.Type.REPLY)
        assertThat(notification.content).isEqualTo("대댓글 테스트입니다")

        consumer.close()
    }

    private fun createTestConsumer(): KafkaConsumer<String, String> {
        val consumerProps = Properties().apply {
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
            put(ConsumerConfig.GROUP_ID_CONFIG, "test-group-${UUID.randomUUID()}")
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
            put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false)
        }
        return KafkaConsumer(consumerProps)
    }
}
