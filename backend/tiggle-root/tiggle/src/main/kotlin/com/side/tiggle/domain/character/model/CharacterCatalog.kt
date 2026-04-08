package com.side.tiggle.domain.character.model

import jakarta.persistence.*

@Entity
@Table(name = "character_catalog")
class CharacterCatalog(
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    val tier: CharacterTier,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    val path: CharacterPath,

    @Column(nullable = false)
    val level: Int,

    @Column(nullable = false, length = 50)
    val name: String,

    @Column(name = "name_en", nullable = false, length = 50)
    val nameEn: String,

    @Column(length = 200)
    val description: String? = null,

    @Column(name = "required_exp", nullable = false)
    val requiredExp: Int,

    @Column(name = "image_key", nullable = false, length = 100)
    val imageKey: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
