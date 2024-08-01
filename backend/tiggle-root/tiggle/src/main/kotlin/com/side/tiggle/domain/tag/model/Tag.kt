package com.side.tiggle.domain.tag.model

import com.side.tiggle.domain.member.model.Member
import javax.persistence.*

@Entity
@Table(name = "tags")
class Tag(
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "defaults", nullable = false)
    val defaults: Boolean = false
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null
}