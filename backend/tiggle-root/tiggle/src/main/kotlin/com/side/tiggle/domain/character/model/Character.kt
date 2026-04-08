package com.side.tiggle.domain.character.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "characters")
class Character(
    @Column(name = "member_id", nullable = false, unique = true)
    val memberId: Long,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var stage: CharacterStage = CharacterStage.EGG,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var tier: CharacterTier? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "character_path", length = 30)
    var characterPath: CharacterPath? = null,

    @Column(nullable = false)
    var level: Int = 0,

    @Column(nullable = false)
    var experience: Int = 0,

    // Egg state
    @Column(name = "egg_records", nullable = false)
    var eggRecords: Int = 0,

    @Column(name = "egg_phase", nullable = false)
    var eggPhase: Int = 0,

    // Hatch stats snapshot
    @Column(name = "hatch_max_streak")
    var hatchMaxStreak: Int? = null,

    @Column(name = "hatch_category_count")
    var hatchCategoryCount: Int? = null,

    @Column(name = "hatch_avg_amount")
    var hatchAvgAmount: Int? = null,

    @Column(name = "hatch_frequency_score")
    var hatchFrequencyScore: Double? = null,

    @Column(name = "hatched_at")
    var hatchedAt: LocalDateTime? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
}
