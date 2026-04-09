package com.side.tiggle.domain.item.repository

import com.side.tiggle.domain.item.model.ItemCatalog
import com.side.tiggle.domain.item.model.ItemSlot
import com.side.tiggle.domain.item.model.ItemTier
import org.springframework.data.jpa.repository.JpaRepository

interface ItemCatalogRepository : JpaRepository<ItemCatalog, Long> {
    fun findBySlot(slot: ItemSlot): List<ItemCatalog>
    fun findByTier(tier: ItemTier): List<ItemCatalog>
}
