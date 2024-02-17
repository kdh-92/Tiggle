package com.side.tiggle.domain.asset.api

import com.side.tiggle.domain.asset.dto.AssetDto
import com.side.tiggle.domain.asset.dto.req.AssetUpdateReqDto
import com.side.tiggle.domain.asset.dto.resp.AssetRespDto
import com.side.tiggle.domain.asset.service.AssetService
import com.side.tiggle.global.common.constants.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/asset")
class AssetApiController(
        private val assetService: AssetService
){

    @PostMapping
    fun createAsset(@RequestBody assetDto: AssetDto): ResponseEntity<AssetRespDto> {
        return ResponseEntity.ok(AssetRespDto.fromEntity(assetService.createAsset(assetDto)))
    }

    @GetMapping("/{id}")
    fun getAsset(@PathVariable("id") assetId: Long): ResponseEntity<AssetRespDto> {
        return ResponseEntity.ok(AssetRespDto.fromEntity(assetService.getAsset(assetId)))
    }

    @GetMapping("/all")
    fun getAllAsset(): ResponseEntity<List<AssetRespDto>> {
        val allAssets = assetService.getAllAsset()
        val assetRespDtos = allAssets.map { AssetRespDto.fromEntity(it) }
        return ResponseEntity.ok(assetRespDtos)
    }

    @PutMapping("/{id}")
    fun updateAsset(
        @PathVariable("id") assetId: Long,
        @RequestBody dto: AssetUpdateReqDto
    ): ResponseEntity<AssetRespDto> {
        return ResponseEntity.ok(AssetRespDto.fromEntity(assetService.updateAsset(assetId, dto)));
    }

    @DeleteMapping("/{id}")
    fun deleteAsset(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable("id") assetId: Long
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(assetService.deleteAsset(memberId, assetId))
    }
}