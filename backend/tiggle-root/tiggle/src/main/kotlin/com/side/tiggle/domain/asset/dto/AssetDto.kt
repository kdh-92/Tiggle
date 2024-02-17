package com.side.tiggle.domain.asset.dto

import com.side.tiggle.domain.asset.model.Asset

data class AssetDto(
    val name: String,
    val defaults: Boolean
) {

    fun toEntity(): Asset {
        return Asset(
            name = this.name,
            defaults = this.defaults
        )
    }
    companion object {
        fun fromEntity(asset: Asset): AssetDto {
            return AssetDto(
                name = asset.name,
                defaults = asset.defaults
            )
        }
    }
}