package com.side.tiggle.domain.item.dto.resp

import com.side.tiggle.domain.item.model.ItemSlot
import com.side.tiggle.domain.item.model.ItemTier
import java.time.LocalDateTime

data class MemberItemRespDto(
    val itemId: Long,
    val name: String,
    val nameEn: String,
    val slot: ItemSlot,
    val tier: ItemTier,
    val imageKey: String,
    val acquiredAt: LocalDateTime
)
