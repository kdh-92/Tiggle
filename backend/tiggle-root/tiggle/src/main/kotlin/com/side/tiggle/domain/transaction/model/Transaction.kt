package com.side.tiggle.domain.transaction.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.reaction.model.Reaction
import com.side.tiggle.global.common.model.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDate
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PastOrPresent
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size


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

    @Column(name = "image_url", length = 255)
    val imageUrl: String? = null,

    @Column(nullable = false)
    @Positive(message = "금액은 양수여야 합니다")
    var amount: Int,

    @Column(nullable = false)
    @PastOrPresent(message = "미래 날짜는 입력할 수 없습니다")
    var date: LocalDate,

    @Column(nullable = false, length = 255)
    @NotBlank(message = "내용은 필수입니다")
    @Size(max = 255, message = "내용은 255자 이하여야 합니다")
    var content: String,

    @Column(nullable = false, length = 255)
    @NotBlank(message = "사유는 필수입니다")
    @Size(max = 255, message = "사유는 255자 이하여야 합니다")
    var reason: String,

    @Convert(converter = TagNamesConverter::class)
    @Size(max = 10, message = "태그는 최대 10개까지 가능합니다")
    var tagNames: List<String>? = null,
): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(mappedBy = "tx", fetch = FetchType.LAZY)
    @JsonIgnore
    var reactionList: MutableList<Reaction> = mutableListOf()
}