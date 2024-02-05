package com.side.tiggle.domain.asset.dto.resp

import com.side.tiggle.domain.asset.model.Asset

data class AssetRespDto(
    val id: Long,
    val name: String,
    val defaults: Boolean
) {
    companion object {
        fun fromEntity(asset: Asset): AssetRespDto {
            return AssetRespDto(
                name = asset.name,
                id = asset.id!!,
                defaults = asset.defaults
            )
        }
    }
}