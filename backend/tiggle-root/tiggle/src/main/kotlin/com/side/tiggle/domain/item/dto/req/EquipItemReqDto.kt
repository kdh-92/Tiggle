package com.side.tiggle.domain.item.dto.req

import com.side.tiggle.domain.item.model.ItemSlot

data class EquipItemReqDto(
    val slot: ItemSlot,
    val itemId: Long?
)
