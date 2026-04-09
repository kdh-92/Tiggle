package com.side.tiggle.domain.achievement.model

import jakarta.persistence.*

@Entity
@Table(name = "achievements")
class Achievement(
    @Column(nullable = false, unique = true, length = 50)
    val code: String,

    @Column(nullable = false, length = 100)
    val name: String,

    @Column(length = 500)
    val description: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_type", nullable = false, length = 30)
    val conditionType: AchievementConditionType,

    @Column(name = "condition_value", nullable = false)
    val conditionValue: Int,

    @Column(name = "reward_item_id")
    val rewardItemId: Long? = null,

    @Column(name = "reward_exp", nullable = false)
    val rewardExp: Int = 0
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
