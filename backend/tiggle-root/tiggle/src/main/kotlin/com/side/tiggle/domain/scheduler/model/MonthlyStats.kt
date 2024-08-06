package com.side.tiggle.domain.scheduler.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.member.model.Member
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "monthly_stats")
class MonthlyStats(
    @JsonIgnore
    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val member: Member,
    val monthlyStart: LocalDate,
    val monthlyEnd: LocalDate,
    val totalAmount: Int,
    val highestAmount: Int,
    val lowestAmount: Int,
    @JsonIgnore
    @JoinColumn(name = "most_frequent_category_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val category: Category
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}