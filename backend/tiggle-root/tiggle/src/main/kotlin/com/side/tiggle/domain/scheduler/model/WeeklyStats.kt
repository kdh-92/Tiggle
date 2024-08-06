package com.side.tiggle.domain.scheduler.model

import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.member.model.Member
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "weekly_stats")
class WeeklyStats(
    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val member: Member,
    val weeklyStart: LocalDate,
    val weeklyEnd: LocalDate,
    val totalAmount: Int,
    val highestAmount: Int,
    val lowestAmount: Int,
    @JoinColumn(name = "most_frequent_category_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val category: Category
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}