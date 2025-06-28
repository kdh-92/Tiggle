package com.side.tiggle.domain.transaction.model

import com.side.tiggle.global.common.model.BaseEntity
import org.hibernate.annotations.SQLDelete
import java.time.LocalDate
import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction


@Entity
@SQLDelete(sql = "UPDATE transactions SET deleted_at = CURRENT_TIMESTAMP, deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Table(name = "transactions")
class Transaction(
    val memberId: Long,
    val categoryId: Long,
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
}
