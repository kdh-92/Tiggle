package com.side.tiggle.domain.item.dto.resp

import com.side.tiggle.domain.item.model.ItemSlot
import com.side.tiggle.domain.item.model.ItemTier

data class ItemCatalogRespDto(
    val id: Long,
    val name: String,
    val nameEn: String,
    val description: String?,
    val slot: ItemSlot,
    val tier: ItemTier,
    val imageKey: String,
    val requiredCharacterLevel: Int,
    val unlocked: Boolean
)
