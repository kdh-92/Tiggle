package com.side.tiggle.domain.transaction.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.reaction.model.Reaction
import com.side.tiggle.global.common.model.BaseEntity
import org.hibernate.annotations.SQLDelete
import java.time.LocalDate
import jakarta.persistence.*
import jakarta.validation.constraints.PastOrPresent
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import org.hibernate.annotations.SQLRestriction


@Entity
@SQLDelete(sql = "UPDATE transactions SET deleted_at = CURRENT_TIMESTAMP, deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Table(name = "transactions")
class Transaction(
    @JsonIgnore
    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val member: Member,

    @JsonIgnore
    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val category: Category,

    @Column(length = 1000)
    val imageUrl: String? = null,

    @Column(nullable = false)
    @PositiveOrZero(message = "금액은 0원 이상이어야 합니다")
    var amount: Int,

    @Column(nullable = false)
    @field:PastOrPresent(message = "지출 날짜는 오늘이거나 이전이어야 합니다")
    var date: LocalDate,

    @Column(nullable = false, length = 20)
    @Size(max = 20, message = "제목은 20자 이하여야 합니다")
    var content: String,

    @Column(nullable = false, length = 300)
    @Size(max = 300, message = "설명는 300자 이하여야 합니다")
    var reason: String,

    @Convert(converter = TagNamesConverter::class)
    var tagNames: List<String>? = null,
): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(mappedBy = "tx", fetch = FetchType.LAZY)
    @JsonIgnore
    var reactionList: MutableList<Reaction> = mutableListOf()
}
