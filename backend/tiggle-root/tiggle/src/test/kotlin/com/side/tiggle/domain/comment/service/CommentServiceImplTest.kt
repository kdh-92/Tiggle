package com.side.tiggle.domain.comment.service

import com.side.tiggle.domain.comment.dto.req.CommentCreateReqDto
import com.side.tiggle.domain.comment.dto.req.CommentUpdateReqDto
import com.side.tiggle.domain.comment.exception.CommentException
import com.side.tiggle.domain.comment.exception.error.CommentErrorCode
import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.comment.repository.CommentRepository
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.member.service.MemberService
import com.side.tiggle.domain.notification.service.NotificationService
import com.side.tiggle.domain.transaction.dto.internal.TransactionInfo
import com.side.tiggle.support.factory.TestCommentFactory
import com.side.tiggle.support.factory.TestMemberFactory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

class CommentServiceImplTest : StringSpec({

    val commentRepository = mockk<CommentRepository>()
    val notificationService = mockk<NotificationService>(relaxed = true)
    val memberService = mockk<MemberService>()

    val commentService = CommentServiceImpl(commentRepository, notificationService, memberService)

    "부모 댓글 수를 반환한다" {
        every { commentRepository.countAllByTxIdAndParentId(1L, null) } returns 3

        val result = commentService.getParentCount(1L)

        result shouldBe 3
    }

    "트랜잭션 ID로 부모 댓글을 페이지로 조회한다" {
        val receiver = TestMemberFactory.create(id = 3L)
        val comment = TestCommentFactory.create(id = 1L, receiver = receiver)
        val page = PageImpl(listOf(comment), PageRequest.of(0, 10), 1)

        every { commentRepository.findByTxIdAndParentIdNullWithSender(1L, any()) } returns page
        every { commentRepository.countAllByTxIdAndParentId(any(), any()) } returns 0

        val result = commentService.getParentsByTxId(1L, 0, 10)

        result.pageNumber shouldBe 0
        result.comments.size shouldBe 1

        val commentResponse = result.comments.first()
        commentResponse.sender.id shouldBe 1L
        commentResponse.receiver.id shouldBe 3L
    }

    "부모 ID로 자식 댓글을 페이지로 조회한다" {
        val receiver = TestMemberFactory.create(id = 3L)
        val comment = TestCommentFactory.create(id = 1L, receiver = receiver)
        val page = PageImpl(listOf(comment), PageRequest.of(0, 10), 1)

        every { commentRepository.findByParentIdWithSender(1L, any()) } returns page
        every { commentRepository.countAllByTxIdAndParentId(any(), any()) } returns 0

        val result = commentService.getChildrenByParentId(1L, 0, 10)

        result.comments.size shouldBe 1
        result.pageNumber shouldBe 0
    }

    "댓글을 생성한다 - 부모 댓글 없이" {
        val tx = mockk<TransactionInfo> {
            every { memberId } returns 2L
        }

        val sender = mockk<Member> { every { id } returns 1L }
        val receiver = mockk<Member> { every { id } returns 2L }

        val commentDto = mockk<CommentCreateReqDto> {
            every { parentId } returns null
            every { toEntity(sender, receiver) } returns mockk()
        }

        every { memberService.getMemberReference(1L) } returns sender
        every { memberService.getMemberReference(2L) } returns receiver
        every { commentRepository.save(any()) } returns mockk()

        commentService.createComment(1L, tx, commentDto)

        verify { commentRepository.save(any()) }
        verify { notificationService.sendCommentNotification(any(), null, tx, 1L) }
    }


    "댓글을 수정한다 - 정상 케이스" {
        val sender = TestMemberFactory.create(id = 1L, nickname = "tester1")
        val receiver = TestMemberFactory.create(id = 2L, nickname = "tester2")

        val comment = Comment(
            txId = 1L,
            sender = sender,
            receiver = receiver,
            content = "Old content"
        )

        every { commentRepository.findByIdWithSender(1L) } returns comment
        every { commentRepository.save(any()) } returnsArgument 0

        val dto = CommentUpdateReqDto(content = "Updated content")

        commentService.updateComment(1L, sender.id, dto)

        verify { commentRepository.save(comment) }
        comment.content shouldBe "Updated content"
    }

    "댓글 수정 시 댓글이 존재하지 않으면 예외 발생" {
        every { commentRepository.findByIdWithSender(1L) } returns null

        shouldThrow<CommentException> {
            commentService.updateComment(1L, 1L, CommentUpdateReqDto("Test"))
        }.getErrorCode() shouldBe CommentErrorCode.COMMENT_NOT_FOUND
    }

    "댓글 수정 시 작성자가 아니면 예외 발생" {
        val comment = mockk<Comment>(relaxed = true)
        every { comment.sender.id } returns 2L
        every { commentRepository.findByIdWithSender(1L) } returns comment

        shouldThrow<CommentException> {
            commentService.updateComment(1L, 1L, CommentUpdateReqDto("Test"))
        }.getErrorCode() shouldBe CommentErrorCode.COMMENT_ACCESS_DENIED
    }

    "댓글을 삭제한다 - 정상 케이스" {
        val comment = mockk<Comment>(relaxed = true)
        every { comment.sender.id } returns 1L
        every { commentRepository.findByIdWithSender(1L) } returns comment
        every { commentRepository.delete(comment) } returns Unit

        commentService.deleteComment(1L, 1L)

        verify { commentRepository.delete(comment) }
    }

    "댓글 삭제 시 존재하지 않으면 예외 발생" {
        every { commentRepository.findByIdWithSender(1L) } returns null

        shouldThrow<CommentException> {
            commentService.deleteComment(1L, 1L)
        }.getErrorCode() shouldBe CommentErrorCode.COMMENT_NOT_FOUND
    }

    "댓글 삭제 시 작성자가 아니면 예외 발생" {
        val comment = mockk<Comment>(relaxed = true)
        every { comment.sender.id } returns 2L
        every { commentRepository.findByIdWithSender(1L) } returns comment

        shouldThrow<CommentException> {
            commentService.deleteComment(1L, 1L)
        }.getErrorCode() shouldBe CommentErrorCode.COMMENT_ACCESS_DENIED
    }

    "부모 댓글이 존재할 때 부모 댓글을 조회한다" {
        // given
        val parentId = 1L
        val tx = mockk<TransactionInfo> {
            every { memberId } returns 2L
        }
        val parentComment = mockk<Comment>()

        every { commentRepository.findByIdWithSender(parentId) } returns parentComment
        every { commentRepository.save(any()) } returns mockk()

        val req = CommentCreateReqDto(
            txId = 1L,
            content = "대댓글입니다",
            parentId = parentId
        )

        // when
        commentService.createComment(1L, tx, req)

        // then
        verify { commentRepository.findByIdWithSender(parentId) }
    }

    "댓글 생성 시 부모 댓글이 존재하지 않으면 예외를 던진다" {
        val tx = mockk<TransactionInfo> {
            every { memberId } returns 2L
        }

        val sender = mockk<Member> { every { id } returns 1L }
        val receiver = mockk<Member> { every { id } returns 2L }

        val commentDto = mockk<CommentCreateReqDto> {
            every { parentId } returns 999L
            every { toEntity(sender, receiver) } returns mockk()
        }

        every { memberService.getMemberReference(1L) } returns sender
        every { memberService.getMemberReference(2L) } returns receiver
        every { commentRepository.findByIdWithSender(999L) } returns null

        shouldThrow<CommentException> {
            commentService.createComment(1L, tx, commentDto)
        }
    }
})
