package com.side.tiggle.domain.category.model

import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.global.common.model.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLRestriction("deleted = false")
@Table(name = "categories")
class Category(
    var name: String,
    var defaults: Boolean,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member
): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
