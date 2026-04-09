package com.side.tiggle.domain.item.repository

import com.side.tiggle.domain.item.model.MemberEquipment
import org.springframework.data.jpa.repository.JpaRepository

interface MemberEquipmentRepository : JpaRepository<MemberEquipment, Long> {
    fun findByMemberId(memberId: Long): MemberEquipment?
}
