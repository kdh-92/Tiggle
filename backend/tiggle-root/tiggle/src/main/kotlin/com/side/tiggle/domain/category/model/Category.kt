package com.side.tiggle.domain.category.model

import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.global.common.model.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.Where

@Entity
@Where(clause =" deleted = false") // 딜리트 플래그가 false인 데이터만 조회하도록 수정
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
