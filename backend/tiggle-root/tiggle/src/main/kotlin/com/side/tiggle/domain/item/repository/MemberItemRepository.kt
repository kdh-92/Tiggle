package com.side.tiggle.domain.item.repository

import com.side.tiggle.domain.item.model.MemberItem
import org.springframework.data.jpa.repository.JpaRepository

interface MemberItemRepository : JpaRepository<MemberItem, Long> {
    fun findAllByMemberId(memberId: Long): List<MemberItem>
    fun existsByMemberIdAndItemId(memberId: Long, itemId: Long): Boolean
}
