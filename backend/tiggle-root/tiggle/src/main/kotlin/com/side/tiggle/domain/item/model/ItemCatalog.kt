package com.side.tiggle.domain.item.model

import jakarta.persistence.*

@Entity
@Table(name = "item_catalog")
class ItemCatalog(
    @Column(nullable = false, length = 100)
    val name: String,

    @Column(name = "name_en", nullable = false, length = 100)
    val nameEn: String,

    @Column(length = 500)
    val description: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    val slot: ItemSlot,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    val tier: ItemTier,

    @Column(name = "image_key", nullable = false, length = 200)
    val imageKey: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "unlock_type", nullable = false, length = 20)
    val unlockType: UnlockType,

    @Column(name = "unlock_condition", length = 200)
    val unlockCondition: String? = null,

    @Column(name = "required_character_level", nullable = false)
    val requiredCharacterLevel: Int = 0
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
