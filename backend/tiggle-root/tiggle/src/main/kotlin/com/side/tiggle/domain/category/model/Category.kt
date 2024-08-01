package com.side.tiggle.domain.category.model

import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.global.common.model.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "categories")
class Category(
    var name: String,
    var defaults: Boolean
): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null
}