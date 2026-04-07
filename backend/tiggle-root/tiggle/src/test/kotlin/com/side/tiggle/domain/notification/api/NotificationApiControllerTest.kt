package com.side.tiggle.domain.notification.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.side.tiggle.domain.comment.dto.resp.CommentRespDto
import com.side.tiggle.domain.member.dto.internal.MemberInfo
import com.side.tiggle.domain.member.dto.resp.MemberRespDto
import com.side.tiggle.domain.notification.dto.resp.NotificationRespDto
import com.side.tiggle.domain.notification.service.NotificationService
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class NotificationApiControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @MockitoBean private val notificationService: NotificationService,
) : StringSpec() {

    override suspend fun beforeEach(testCase: TestCase) {
        reset(notificationService)
    }

    private fun createMockNotificationRespDto(
        id: Long = 1L,
        title: String = "테스트 알림",
        content: String = "테스트 내용",
        viewedAt: LocalDateTime? = null
    ): NotificationRespDto {
        val notification = NotificationRespDto(
            id = id,
            title = title,
            content = content,
            createdAt = LocalDateTime.now(),
            viewedAt = viewedAt
        )

        // 관련 객체들 설정
        notification.sender = MemberRespDto(
            id = 2L,
            email = "sender@test.com",
            profileUrl = null,
            nickname = "발신자",
            birth = LocalDate.of(1990, 1, 1)
        )

        notification.receiver = MemberRespDto(
            id = 3L,
            email = "receiver@test.com",
            profileUrl = null,
            nickname = "수신자",
            birth = LocalDate.of(1995, 5, 5)
        )

        notification.comment = CommentRespDto(
            id = 1L,
            txId = 1L,
            parentId = null,
            content = "댓글 내용",
            createdAt = LocalDateTime.now(),
            sender = MemberInfo(
                id = 2L,
                nickname = "발신자",
                profileUrl = null
            ),
            receiver = MemberInfo(
                id = 3L,
                nickname = "수신자",
                profileUrl = null
            )
        )

        return notification
    }

    init {
        "GET /api/v1/notification - 모든 알림 조회 성공" {
            // given
            val memberId = 1L // 테스트 환경에서 헤더값
            val expectedNotifications = listOf(
                createMockNotificationRespDto(1L, "알림1", "내용1"),
                createMockNotificationRespDto(2L, "알림2", "내용2"),
                createMockNotificationRespDto(3L, "알림3", "내용3")
            )

            given(notificationService.getAllByMemberId(memberId)).willReturn(expectedNotifications)

            // when & then
            mockMvc.perform(
                get("/api/v1/notification")
                    .header("x-member-id", "1")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray)
                .andExpect(jsonPath("$.data.length()").value(3))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].title").value("알림1"))
                .andExpect(jsonPath("$.data[0].content").value("내용1"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[2].id").value(3))

            verify(notificationService).getAllByMemberId(memberId)
        }

        "GET /api/v1/notification - 다른 memberId로 알림 조회 성공" {
            // given
            val overrideMemberId = 5L
            val expectedNotifications = listOf(
                createMockNotificationRespDto(1L, "오버라이드 알림", "오버라이드 내용")
            )

            given(notificationService.getAllByMemberId(overrideMemberId)).willReturn(expectedNotifications)

            // when & then
            mockMvc.perform(
                get("/api/v1/notification")
                    .header("x-member-id", overrideMemberId.toString())
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray)
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].title").value("오버라이드 알림"))

            verify(notificationService).getAllByMemberId(overrideMemberId)
        }

        "GET /api/v1/notification - 알림이 없을 때 빈 배열 반환" {
            // given
            val memberId = 1L

            given(notificationService.getAllByMemberId(memberId)).willReturn(emptyList())

            // when & then
            mockMvc.perform(
                get("/api/v1/notification")
                    .header("x-member-id", "1")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray)
                .andExpect(jsonPath("$.data.length()").value(0))

            verify(notificationService).getAllByMemberId(memberId)
        }

        "PUT /api/v1/notification/{id} - 알림 읽음 처리 성공" {
            // given
            val memberId = 1L
            val notificationId = 1L

            doNothing().`when`(notificationService).readNotificationById(any(), any())

            // when & then
            mockMvc.perform(
                put("/api/v1/notification/$notificationId")
                    .header("x-member-id", "1")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))

            verify(notificationService).readNotificationById(memberId, notificationId)
        }

        "PUT /api/v1/notification/{id} - 여러 알림 읽음 처리 테스트" {
            // given
            val memberId = 1L
            val notificationIds = listOf(1L, 2L, 3L)

            doNothing().`when`(notificationService).readNotificationById(any(), any())

            // when & then - 각각 개별 요청으로 처리
            notificationIds.forEach { notificationId ->
                mockMvc.perform(
                    put("/api/v1/notification/$notificationId")
                        .header("x-member-id", "1")
                )
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.success").value(true))

                verify(notificationService).readNotificationById(memberId, notificationId)
            }
        }

        "GET /api/v1/notification - 읽음/안읽음 상태가 포함된 알림 조회" {
            // given
            val memberId = 1L
            val readNotification = createMockNotificationRespDto(
                id = 1L,
                title = "읽은 알림",
                viewedAt = LocalDateTime.now()
            )
            val unreadNotification = createMockNotificationRespDto(
                id = 2L,
                title = "안읽은 알림",
                viewedAt = null
            )

            val expectedNotifications = listOf(readNotification, unreadNotification)

            given(notificationService.getAllByMemberId(memberId)).willReturn(expectedNotifications)

            // when & then
            mockMvc.perform(
                get("/api/v1/notification")
                    .header("x-member-id", "1")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].viewedAt").exists())
                .andExpect(jsonPath("$.data[1].viewedAt").doesNotExist())

            verify(notificationService).getAllByMemberId(memberId)
        }
    }
}