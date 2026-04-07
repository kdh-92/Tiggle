package com.side.tiggle.domain.notification.service

import com.side.tiggle.domain.notification.NotificationProducer
import com.side.tiggle.domain.notification.dto.NotificationProduceDto
import com.side.tiggle.domain.notification.exception.NotificationException
import com.side.tiggle.domain.notification.exception.error.NotificationErrorCode
import com.side.tiggle.domain.notification.model.Notification
import com.side.tiggle.domain.notification.model.NotificationType
import com.side.tiggle.domain.notification.repository.NotificationRepository
import com.side.tiggle.domain.transaction.dto.internal.TransactionInfo
import com.side.tiggle.support.factory.TestMemberFactory
import com.side.tiggle.support.factory.TestCommentFactory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class NotificationServiceImplTest : StringSpec({

    val notificationRepository: NotificationRepository = mockk()
    val notificationProducer: NotificationProducer = mockk()
    val notificationService: NotificationService = NotificationServiceImpl(
        notificationRepository,
        notificationProducer
    )

    beforeEach {
        clearAllMocks()
    }

    "회원 ID로 모든 알림을 조회합니다 - sender가 있는 경우" {
        // given
        val memberId = 1L
        val senderId = 2L
        val commentId = 10L

        val sender = TestMemberFactory.create(id = senderId)
        val receiver = TestMemberFactory.create(id = memberId)
        val comment = TestCommentFactory.create(
            id = commentId,
            sender = sender
        )

        val notification = Notification(
            type = NotificationType.COMMENT,
            title = "새 댓글",
            content = "댓글이 달렸습니다"
        ).apply {
            id = 1L
            this.sender = sender
            this.receiver = receiver
            this.comment = comment
            createdAt = LocalDateTime.now()
        }

        every { notificationRepository.findAllByReceiverId(memberId) } returns listOf(notification)

        // when
        val result = notificationService.getAllByMemberId(memberId)

        // then
        result.size shouldBe 1
        result[0].sender shouldNotBe null
        result[0].sender?.id shouldBe senderId
        result[0].receiver?.id shouldBe memberId
        result[0].comment?.id shouldBe commentId
    }

    "회원 ID로 모든 알림을 조회합니다 - sender가 없는 경우" {
        // given
        val memberId = 1L
        val commentId = 10L

        val receiver = TestMemberFactory.create(id = memberId)
        val comment = TestCommentFactory.create(id = commentId)

        val notification = Notification(
            type = NotificationType.COMMENT,
            title = "새 댓글",
            content = "댓글이 달렸습니다"
        ).apply {
            id = 1L
            this.sender = null
            this.receiver = receiver
            this.comment = comment
            createdAt = LocalDateTime.now()
        }

        every { notificationRepository.findAllByReceiverId(memberId) } returns listOf(notification)

        // when
        val result = notificationService.getAllByMemberId(memberId)

        // then
        result.size shouldBe 1
        result[0].sender shouldBe null
        result[0].receiver?.id shouldBe memberId
        result[0].comment?.id shouldBe commentId
    }

    "회원 ID로 알림 조회 시 빈 목록을 반환합니다" {
        // given
        val memberId = 1L

        every { notificationRepository.findAllByReceiverId(memberId) } returns emptyList()

        // when
        val result = notificationService.getAllByMemberId(memberId)

        // then
        result.size shouldBe 0
    }

    "댓글 알림을 전송합니다 (일반 댓글)" {
        // given
        val senderId = 1L
        val txOwnerId = 2L
        val commentId = 10L
        val txId = 20L

        val sender = TestMemberFactory.create(id = senderId)
        val comment = TestCommentFactory.create(
            id = commentId,
            content = "새로운 댓글입니다",
            sender = sender
        )

        val tx = TransactionInfo(
            id = txId,
            memberId = txOwnerId,
            categoryId = 1L,
            txType = com.side.tiggle.domain.transaction.model.TxType.OUTCOME,
            amount = 10000,
            date = LocalDate.now(),
            content = "점심식사",
            reason = "회사 근처 식당"
        )

        val capturedSlot = slot<NotificationProduceDto>()
        every { notificationProducer.send(capture(capturedSlot)) } just runs

        // when
        notificationService.sendCommentNotification(comment, null, tx, senderId)

        // then
        verify(exactly = 1) { notificationProducer.send(any()) }

        capturedSlot.captured.apply {
            receiverId shouldBe txOwnerId
            senderId shouldBe senderId
            title shouldBe "점심식사"
            content shouldBe "새로운 댓글입니다"
            txId shouldBe txId
            commentId shouldBe commentId
            type shouldBe NotificationProduceDto.Type.COMMENT
        }
    }

    "대댓글 알림을 전송합니다 - parentComment.sender.id 경로 테스트" {
        // given
        val senderId = 1L
        val parentCommentOwnerId = 2L
        val txOwnerId = 3L
        val commentId = 10L
        val parentCommentId = 11L
        val txId = 20L

        val sender = TestMemberFactory.create(id = senderId)
        val parentCommentOwner = TestMemberFactory.create(id = parentCommentOwnerId)

        val comment = TestCommentFactory.create(
            id = commentId,
            content = "대댓글입니다",
            sender = sender
        )

        val parentComment = TestCommentFactory.create(
            id = parentCommentId,
            sender = parentCommentOwner
        )

        val tx = TransactionInfo(
            id = txId,
            memberId = txOwnerId,
            categoryId = 1L,
            txType = com.side.tiggle.domain.transaction.model.TxType.OUTCOME,
            amount = 10000,
            date = LocalDate.now(),
            content = "점심식사",
            reason = "회사 근처 식당"
        )

        val capturedSlot = slot<NotificationProduceDto>()
        every { notificationProducer.send(capture(capturedSlot)) } just runs

        // when
        notificationService.sendCommentNotification(comment, parentComment, tx, senderId)

        // then
        verify(exactly = 1) { notificationProducer.send(any()) }

        capturedSlot.captured.apply {
            // parentComment?.sender?.id 경로가 실행됨
            receiverId shouldBe parentCommentOwnerId
            type shouldBe NotificationProduceDto.Type.REPLY
        }
    }

    "일반 댓글 알림을 전송합니다 - tx.memberId 경로 테스트" {
        // given
        val senderId = 1L
        val txOwnerId = 2L
        val commentId = 10L
        val txId = 20L

        val sender = TestMemberFactory.create(id = senderId)
        val comment = TestCommentFactory.create(
            id = commentId,
            content = "일반 댓글입니다",
            sender = sender
        )

        val tx = TransactionInfo(
            id = txId,
            memberId = txOwnerId,
            categoryId = 1L,
            txType = com.side.tiggle.domain.transaction.model.TxType.OUTCOME,
            amount = 10000,
            date = LocalDate.now(),
            content = "점심식사",
            reason = "회사 근처 식당"
        )

        val capturedSlot = slot<NotificationProduceDto>()
        every { notificationProducer.send(capture(capturedSlot)) } just runs

        // when
        notificationService.sendCommentNotification(comment, null, tx, senderId)

        // then
        verify(exactly = 1) { notificationProducer.send(any()) }

        capturedSlot.captured.apply {
            receiverId shouldBe txOwnerId
            type shouldBe NotificationProduceDto.Type.COMMENT
        }
    }

    "알림 읽음 처리 시 권한이 있으면 정상 처리합니다" {
        // given
        val memberId = 1L
        val notificationId = 10L

        val receiver = TestMemberFactory.create(id = memberId)
        val notification = Notification(
            type = NotificationType.COMMENT,
            title = "새 댓글",
            content = "댓글이 달렸습니다"
        ).apply {
            id = notificationId
            this.receiver = receiver
            createdAt = LocalDateTime.now()
            viewedAt = null
        }

        every { notificationRepository.findById(notificationId) } returns Optional.of(notification)
        every { notificationRepository.save(notification) } returns notification

        // when
        notificationService.readNotificationById(memberId, notificationId)

        // then
        notification.viewedAt shouldNotBe null
        verify(exactly = 1) { notificationRepository.save(notification) }
    }

    "알림 읽음 처리 시 존재하지 않는 알림이면 예외를 던집니다" {
        // given
        val memberId = 1L
        val notificationId = 999L

        every { notificationRepository.findById(notificationId) } returns Optional.empty()

        // when & then
        shouldThrow<NotificationException> {
            notificationService.readNotificationById(memberId, notificationId)
        }.getErrorCode() shouldBe NotificationErrorCode.NOTIFICATION_NOT_FOUND
    }

    "알림 읽음 처리 시 권한이 없으면 예외를 던집니다" {
        // given
        val memberId = 1L
        val otherMemberId = 2L
        val notificationId = 10L

        val otherMember = TestMemberFactory.create(id = otherMemberId)
        val notification = Notification(
            type = NotificationType.COMMENT,
            title = "새 댓글",
            content = "댓글이 달렸습니다"
        ).apply {
            id = notificationId
            this.receiver = otherMember
            createdAt = LocalDateTime.now()
        }

        every { notificationRepository.findById(notificationId) } returns Optional.of(notification)

        // when & then
        shouldThrow<NotificationException> {
            notificationService.readNotificationById(memberId, notificationId)
        }.getErrorCode() shouldBe NotificationErrorCode.NOTIFICATION_ACCESS_DENIED
    }

    "알림 조회 시 receiver가 null이면 예외를 던집니다" {
        // given
        val memberId = 1L
        val senderId = 2L
        val commentId = 10L

        val sender = TestMemberFactory.create(id = senderId)
        val comment = TestCommentFactory.create(
            id = commentId,
            sender = sender
        )

        val notification = Notification(
            type = NotificationType.COMMENT,
            title = "새 댓글",
            content = "댓글이 달렸습니다"
        ).apply {
            id = 1L
            this.sender = sender
            this.receiver = null  // null로 설정
            this.comment = comment
            createdAt = LocalDateTime.now()
        }

        every { notificationRepository.findAllByReceiverId(memberId) } returns listOf(notification)

        // when & then
        shouldThrow<NotificationException> {
            notificationService.getAllByMemberId(memberId)
        }.getErrorCode() shouldBe NotificationErrorCode.NOTIFICATION_NOT_FOUND
    }

    "알림 조회 시 comment가 null이면 comment 필드가 null로 반환됩니다" {
        // given
        val memberId = 1L
        val senderId = 2L

        val sender = TestMemberFactory.create(id = senderId)
        val receiver = TestMemberFactory.create(id = memberId)

        val notification = Notification(
            type = NotificationType.COMMENT,
            title = "새 댓글",
            content = "댓글이 달렸습니다"
        ).apply {
            id = 1L
            this.sender = sender
            this.receiver = receiver
            this.comment = null
            createdAt = LocalDateTime.now()
        }

        every { notificationRepository.findAllByReceiverId(memberId) } returns listOf(notification)

        // when
        val result = notificationService.getAllByMemberId(memberId)

        // then
        result.size shouldBe 1
        result[0].comment shouldBe null
        result[0].receiver?.id shouldBe memberId
    }

    "모든 알림을 읽음 처리합니다" {
        // given
        val memberId = 1L
        val receiver = TestMemberFactory.create(id = memberId)

        val notification1 = Notification(
            type = NotificationType.COMMENT,
            title = "새 댓글 1",
            content = "댓글이 달렸습니다 1"
        ).apply {
            id = 1L
            this.receiver = receiver
            createdAt = LocalDateTime.now()
            viewedAt = null
        }

        val notification2 = Notification(
            type = NotificationType.COMMENT,
            title = "새 댓글 2",
            content = "댓글이 달렸습니다 2"
        ).apply {
            id = 2L
            this.receiver = receiver
            createdAt = LocalDateTime.now()
            viewedAt = null
        }

        every { notificationRepository.findAllByReceiverIdAndViewedAtIsNull(memberId) } returns listOf(notification1, notification2)
        every { notificationRepository.saveAll(any<List<Notification>>()) } returns listOf(notification1, notification2)

        // when
        notificationService.readAllNotifications(memberId)

        // then
        notification1.viewedAt shouldNotBe null
        notification2.viewedAt shouldNotBe null
        verify(exactly = 1) { notificationRepository.saveAll(any<List<Notification>>()) }
    }

    "모든 알림 읽음 처리 시 읽지 않은 알림이 없으면 빈 리스트를 처리합니다" {
        // given
        val memberId = 1L

        every { notificationRepository.findAllByReceiverIdAndViewedAtIsNull(memberId) } returns emptyList()
        every { notificationRepository.saveAll(any<List<Notification>>()) } returns emptyList()

        // when
        notificationService.readAllNotifications(memberId)

        // then
        verify(exactly = 1) { notificationRepository.saveAll(emptyList()) }
    }

    "읽지 않은 알림 개수를 반환합니다" {
        // given
        val memberId = 1L

        every { notificationRepository.countByReceiverIdAndViewedAtIsNull(memberId) } returns 5L

        // when
        val result = notificationService.getUnreadCount(memberId)

        // then
        result shouldBe 5L
        verify(exactly = 1) { notificationRepository.countByReceiverIdAndViewedAtIsNull(memberId) }
    }

    "읽지 않은 알림이 없으면 0을 반환합니다" {
        // given
        val memberId = 1L

        every { notificationRepository.countByReceiverIdAndViewedAtIsNull(memberId) } returns 0L

        // when
        val result = notificationService.getUnreadCount(memberId)

        // then
        result shouldBe 0L
    }

    "알림 읽음 처리 시 receiver가 null이면 예외를 던집니다" {
        // given
        val memberId = 1L
        val notificationId = 10L

        val notification = Notification(
            type = NotificationType.COMMENT,
            title = "새 댓글",
            content = "댓글이 달렸습니다"
        ).apply {
            id = notificationId
            this.receiver = null
            createdAt = LocalDateTime.now()
        }

        every { notificationRepository.findById(notificationId) } returns Optional.of(notification)

        // when & then
        shouldThrow<NotificationException> {
            notificationService.readNotificationById(memberId, notificationId)
        }.getErrorCode() shouldBe NotificationErrorCode.NOTIFICATION_ACCESS_DENIED
    }
})