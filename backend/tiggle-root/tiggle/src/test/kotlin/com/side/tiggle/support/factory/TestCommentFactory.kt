package com.side.tiggle.support.factory

import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.member.model.Member
import java.time.LocalDateTime

object TestCommentFactory {
    fun create(
        id: Long? = null,
        txId: Long = 1L,
        sender: Member = TestMemberFactory.create(id = 1L),
        receiver: Member = TestMemberFactory.create(id = 2L),
        content: String = "테스트 댓글",
    ): Comment {
        val comment = Comment(
            txId = txId,
            sender = sender,
            receiver = receiver,
            content = content,
        )

        // 리플렉션으로 id 주입 (필요한 경우만)
        if (id != null) {
            val field = Comment::class.java.getDeclaredField("id")
            field.isAccessible = true
            field.set(comment, id)
        }

        val field = Comment::class.java.getSuperclass()
            .getDeclaredField("createdAt")
        field.isAccessible = true
        field.set(comment, LocalDateTime.now())

        return comment
    }
}