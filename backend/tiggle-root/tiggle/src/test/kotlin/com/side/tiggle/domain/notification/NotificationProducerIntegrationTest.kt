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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
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
import com.side.tiggle.TiggleApplication
import java.util.Properties
import java.util.UUID

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [TiggleApplication::class]
)
@AutoConfigureMockMvc
@TestPropertySource(properties = [
    "spring.kafka.bootstrap-servers=localhost:9092",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.defer-datasource-initialization=true",
    "spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl",
])
@Sql(value = ["/sql/test-data.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
@Rollback
class NotificationProducerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc
    private val objectMapper = ObjectMapper().registerKotlinModule()

    @Test
    fun `댓글 작성 시 카프카에 알림 메시지가 발행되는지 테스트`() {
        // Given: 테스트용 카프카 컨슈머 준비
        val consumer = createTestConsumer()
        consumer.subscribe(listOf("tiggle-notification"))

        val testStartTime = System.currentTimeMillis()
        Thread.sleep(3000)

        do {
            val oldRecords = KafkaTestUtils.getRecords(consumer, Duration.ofSeconds(1))
        } while (oldRecords.count() > 0)

        // When: 댓글 작성 API 호출
        mockMvc.perform(
            post("/api/v1/comments")
                .header("x-member-id", "10")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "txId": 27,
                    "content": "통합테스트용 댓글입니다-${testStartTime}",
                    "parentId": null
                }
            """.trimIndent())
        ).andExpect(status().isCreated)

        // Then: 새로운 메시지만 확인
        val records: ConsumerRecords<String, String> = KafkaTestUtils.getRecords(
            consumer,
            Duration.ofSeconds(15)
        )

        val testMessages = records.filter { record ->
            val messageJson = record.value()
            messageJson.contains("통합테스트용 댓글입니다-${testStartTime}")
        }

        assertThat(testMessages.size).isEqualTo(1)

        val messageJson = testMessages.first().value()
        val notification = objectMapper.readValue(messageJson, NotificationProduceDto::class.java)

        assertThat(notification.receiverId).isNotNull()
        assertThat(notification.senderId).isEqualTo(-1L)
        assertThat(notification.content).isEqualTo("통합테스트용 댓글입니다-${testStartTime}")
        assertThat(notification.txId).isEqualTo(27L)
        assertThat(notification.type).isIn(
            NotificationProduceDto.Type.COMMENT,
            NotificationProduceDto.Type.REPLY
        )

        consumer.close()
    }

    @Test
    fun `대댓글 작성 시 카프카에 REPLY 타입 메시지가 발행되는지 테스트`() {
        // Given: 테스트용 카프카 컨슈머 준비
        val consumer = createTestConsumer()
        consumer.subscribe(listOf("tiggle-notification"))

        val testId = System.currentTimeMillis()
        Thread.sleep(3000)

        do {
            val oldRecords = KafkaTestUtils.getRecords(consumer, Duration.ofSeconds(1))
        } while (oldRecords.count() > 0)

        // When: 대댓글 작성 API 호출 (고유 식별자 포함)
        mockMvc.perform(
            post("/api/v1/comments")
                .header("x-member-id", "10")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "txId": 27,
                    "content": "대댓글 테스트-${testId}",
                    "parentId": 1
                }
            """.trimIndent())
        ).andExpect(status().isCreated)

        // Then: 카프카에서 REPLY 타입 메시지 확인
        val records = KafkaTestUtils.getRecords(consumer, Duration.ofSeconds(15))

        val testMessages = records.filter { record ->
            val messageJson = record.value()
            messageJson.contains("대댓글 테스트-${testId}")
        }

        assertThat(testMessages.size).isEqualTo(1)

        val messageJson = testMessages.first().value()
        val notification = objectMapper.readValue(messageJson, NotificationProduceDto::class.java)

        assertThat(notification.type).isEqualTo(NotificationProduceDto.Type.REPLY)
        assertThat(notification.content).isEqualTo("대댓글 테스트-${testId}")
        consumer.close()
    }





    private fun createTestConsumer(): KafkaConsumer<String, String> {
        val consumerProps = Properties().apply {
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
            put(ConsumerConfig.GROUP_ID_CONFIG, "test-group-${UUID.randomUUID()}")
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
            put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false)
            put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000)
            put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 10000)
        }
        return KafkaConsumer(consumerProps)
    }
}