package com.side.tiggle.support.factory

import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.transaction.model.Transaction
import java.time.LocalDate

object TestTransactionFactory {
    fun create(
        id: Long? = null,
        member: Member = TestMemberFactory.create(),
        category: Category = Category("식비", false, 1L),
        amount: Int = 10000,
        date: LocalDate = LocalDate.now(),
        content: String = "커피커피",
        reason: String = "멋사 4기 점심식사후 아인슈페너 한잔",
        imageUrls: String? = null,
        tagNames: List<String>? = listOf("커피")
    ): Transaction {
        val transaction = Transaction(
            member = member,
            category = category,
            imageUrls = imageUrls,
            amount = amount,
            date = date,
            content = content,
            reason = reason,
            tagNames = tagNames
        )

        if (id != null) {
            val field = Transaction::class.java.getDeclaredField("id")
            field.isAccessible = true
            field.set(transaction, id)
        }

        return transaction
    }
}