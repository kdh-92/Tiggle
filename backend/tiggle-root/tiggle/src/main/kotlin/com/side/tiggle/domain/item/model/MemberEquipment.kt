package com.side.tiggle.domain.item.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "member_equipment")
class MemberEquipment(
    @Column(name = "member_id", nullable = false, unique = true)
    val memberId: Long,

    @Column(name = "hat_item_id")
    var hatItemId: Long? = null,

    @Column(name = "outfit_item_id")
    var outfitItemId: Long? = null,

    @Column(name = "accessory_item_id")
    var accessoryItemId: Long? = null,

    @Column(name = "background_item_id")
    var backgroundItemId: Long? = null,

    @Column(name = "effect_item_id")
    var effectItemId: Long? = null,

    @Column(name = "title_item_id")
    var titleItemId: Long? = null,

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
