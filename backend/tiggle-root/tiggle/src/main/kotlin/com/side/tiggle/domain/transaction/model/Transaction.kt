package com.side.tiggle.domain.transaction.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.reaction.model.Reaction
import com.side.tiggle.global.common.model.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDate
import javax.persistence.*


@Entity
@SQLDelete(sql = "UPDATE transactions SET deleted_at = CURRENT_TIMESTAMP, deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
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

    val imageUrl: String? = null,
    var amount: Int,
    var date: LocalDate,
    var content: String,
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