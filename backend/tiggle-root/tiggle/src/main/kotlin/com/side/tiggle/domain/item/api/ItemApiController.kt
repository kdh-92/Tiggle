package com.side.tiggle.domain.item.api

import com.side.tiggle.domain.item.dto.req.EquipItemReqDto
import com.side.tiggle.domain.item.dto.resp.EquipmentRespDto
import com.side.tiggle.domain.item.dto.resp.ItemCatalogRespDto
import com.side.tiggle.domain.item.dto.resp.MemberItemRespDto
import com.side.tiggle.domain.item.service.ItemService
import com.side.tiggle.global.common.ApiResponse
import com.side.tiggle.global.common.constants.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/v1/items")
class ItemApiController(
    private val itemService: ItemService
) {

    @GetMapping("/inventory")
    fun getInventory(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<ApiResponse<List<MemberItemRespDto>>> {
        val inventory = itemService.getInventory(memberId)
        return ResponseEntity.ok(ApiResponse.success(inventory))
    }

    @GetMapping("/catalog")
    fun getCatalog(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<ApiResponse<List<ItemCatalogRespDto>>> {
        val catalog = itemService.getCatalog(memberId)
        return ResponseEntity.ok(ApiResponse.success(catalog))
    }

    @PutMapping("/equip")
    fun equipItem(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @RequestBody request: EquipItemReqDto
    ): ResponseEntity<ApiResponse<Void>> {
        itemService.equipItem(memberId, request.slot, request.itemId)
        return ResponseEntity.ok(ApiResponse.success(null))
    }

    @GetMapping("/equipment")
    fun getMyEquipment(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<ApiResponse<EquipmentRespDto>> {
        val equipment = itemService.getEquipment(memberId)
        return ResponseEntity.ok(ApiResponse.success(equipment))
    }

    @GetMapping("/equipment/{memberId}")
    fun getEquipment(
        @PathVariable memberId: Long
    ): ResponseEntity<ApiResponse<EquipmentRespDto>> {
        val equipment = itemService.getEquipment(memberId)
        return ResponseEntity.ok(ApiResponse.success(equipment))
    }
}
