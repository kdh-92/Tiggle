package com.side.tiggle.domain.transaction.model

import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.transaction.enum.TransactionType
import com.side.tiggle.global.common.model.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDate


@Entity
@SQLDelete(sql = "UPDATE transactions SET deleted_at = CURRENT_TIMESTAMP, deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Table(name = "transactions")
class Transaction(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    val category: Category,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: TransactionType,

    @Column(length = 1000)
    val imageUrl: String? = null,

    @Column(nullable = false)
    var amount: Int,

    @Column(nullable = false)
    var date: LocalDate,

    @Column(nullable = false, length = 20)
    var content: String,

    @Column(nullable = false, length = 300)
    var reason: String,

    @Convert(converter = TagNamesConverter::class)
    var tagNames: List<String>? = null,
): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
