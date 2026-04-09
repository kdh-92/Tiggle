package com.side.tiggle.domain.item.service

import com.side.tiggle.domain.item.dto.resp.EquipmentRespDto
import com.side.tiggle.domain.item.dto.resp.ItemCatalogRespDto
import com.side.tiggle.domain.item.dto.resp.MemberItemRespDto
import com.side.tiggle.domain.item.model.ItemSlot

interface ItemService {

    fun getInventory(memberId: Long): List<MemberItemRespDto>

    fun getCatalog(memberId: Long): List<ItemCatalogRespDto>

    fun equipItem(memberId: Long, slot: ItemSlot, itemId: Long?)

    fun getEquipment(memberId: Long): EquipmentRespDto

    fun grantItem(memberId: Long, itemId: Long, reason: String)
}
