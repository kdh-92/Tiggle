package com.side.tiggle.domain.item.service

import com.side.tiggle.domain.item.dto.resp.EquipmentRespDto
import com.side.tiggle.domain.item.dto.resp.ItemCatalogRespDto
import com.side.tiggle.domain.item.dto.resp.MemberItemRespDto
import com.side.tiggle.domain.item.exception.ItemException
import com.side.tiggle.domain.item.exception.error.ItemErrorCode
import com.side.tiggle.domain.item.model.ItemSlot
import com.side.tiggle.domain.item.model.MemberEquipment
import com.side.tiggle.domain.item.model.MemberItem
import com.side.tiggle.domain.item.repository.ItemCatalogRepository
import com.side.tiggle.domain.item.repository.MemberEquipmentRepository
import com.side.tiggle.domain.item.repository.MemberItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ItemServiceImpl(
    private val itemCatalogRepository: ItemCatalogRepository,
    private val memberItemRepository: MemberItemRepository,
    private val memberEquipmentRepository: MemberEquipmentRepository
) : ItemService {

    override fun getInventory(memberId: Long): List<MemberItemRespDto> {
        val memberItems = memberItemRepository.findAllByMemberId(memberId)
        val itemIds = memberItems.map { it.itemId }
        val catalogMap = itemCatalogRepository.findAllById(itemIds).associateBy { it.id }

        return memberItems.mapNotNull { mi ->
            val catalog = catalogMap[mi.itemId] ?: return@mapNotNull null
            MemberItemRespDto(
                itemId = catalog.id!!,
                name = catalog.name,
                nameEn = catalog.nameEn,
                slot = catalog.slot,
                tier = catalog.tier,
                imageKey = catalog.imageKey,
                acquiredAt = mi.acquiredAt
            )
        }
    }

    override fun getCatalog(memberId: Long): List<ItemCatalogRespDto> {
        val allItems = itemCatalogRepository.findAll()
        val ownedItemIds = memberItemRepository.findAllByMemberId(memberId)
            .map { it.itemId }
            .toSet()

        return allItems.map { item ->
            ItemCatalogRespDto(
                id = item.id!!,
                name = item.name,
                nameEn = item.nameEn,
                description = item.description,
                slot = item.slot,
                tier = item.tier,
                imageKey = item.imageKey,
                requiredCharacterLevel = item.requiredCharacterLevel,
                unlocked = item.id!! in ownedItemIds
            )
        }
    }

    @Transactional
    override fun equipItem(memberId: Long, slot: ItemSlot, itemId: Long?) {
        val equipment = memberEquipmentRepository.findByMemberId(memberId)
            ?: memberEquipmentRepository.save(MemberEquipment(memberId = memberId))

        // If unequipping (itemId is null), just clear the slot
        if (itemId == null) {
            setSlot(equipment, slot, null)
            equipment.updatedAt = LocalDateTime.now()
            memberEquipmentRepository.save(equipment)
            return
        }

        // Check item exists
        val catalog = itemCatalogRepository.findById(itemId)
            .orElseThrow { ItemException(ItemErrorCode.ITEM_NOT_FOUND) }

        // Check slot matches
        if (catalog.slot != slot) {
            throw ItemException(ItemErrorCode.ITEM_SLOT_MISMATCH)
        }

        // Check member owns the item
        if (!memberItemRepository.existsByMemberIdAndItemId(memberId, itemId)) {
            throw ItemException(ItemErrorCode.ITEM_NOT_OWNED)
        }

        setSlot(equipment, slot, itemId)
        equipment.updatedAt = LocalDateTime.now()
        memberEquipmentRepository.save(equipment)
    }

    override fun getEquipment(memberId: Long): EquipmentRespDto {
        val equipment = memberEquipmentRepository.findByMemberId(memberId)
        return EquipmentRespDto(
            hatItemId = equipment?.hatItemId,
            outfitItemId = equipment?.outfitItemId,
            accessoryItemId = equipment?.accessoryItemId,
            backgroundItemId = equipment?.backgroundItemId,
            effectItemId = equipment?.effectItemId,
            titleItemId = equipment?.titleItemId
        )
    }

    @Transactional
    override fun grantItem(memberId: Long, itemId: Long, reason: String) {
        if (memberItemRepository.existsByMemberIdAndItemId(memberId, itemId)) {
            return // Already owned, silently ignore
        }

        // Verify item exists in catalog
        if (!itemCatalogRepository.existsById(itemId)) {
            throw ItemException(ItemErrorCode.ITEM_NOT_FOUND)
        }

        val memberItem = MemberItem(
            memberId = memberId,
            itemId = itemId,
            acquireReason = reason
        )
        memberItemRepository.save(memberItem)
    }

    private fun setSlot(equipment: MemberEquipment, slot: ItemSlot, itemId: Long?) {
        when (slot) {
            ItemSlot.HAT -> equipment.hatItemId = itemId
            ItemSlot.OUTFIT -> equipment.outfitItemId = itemId
            ItemSlot.ACCESSORY -> equipment.accessoryItemId = itemId
            ItemSlot.BACKGROUND -> equipment.backgroundItemId = itemId
            ItemSlot.EFFECT -> equipment.effectItemId = itemId
            ItemSlot.TITLE -> equipment.titleItemId = itemId
        }
    }
}
