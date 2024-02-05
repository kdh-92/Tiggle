package com.side.tiggle.domain.asset.service

import com.side.tiggle.domain.asset.dto.AssetDto
import com.side.tiggle.domain.asset.dto.req.AssetUpdateReqDto
import com.side.tiggle.domain.asset.model.Asset
import com.side.tiggle.domain.asset.repository.AssetRepository
import com.side.tiggle.global.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class AssetService(
    private val assetRepository: AssetRepository
) {
    fun createAsset(dto: AssetDto): Asset {
        return assetRepository.save(dto.toEntity())
    }

    fun getAsset(assetId: Long): Asset {
        return assetRepository.findById(assetId)
            .orElseThrow{ NotFoundException() }
    }

    fun getAllAsset(): List<Asset> {
        return assetRepository.findAll()
    }

    fun updateAsset(assetId: Long, dto: AssetUpdateReqDto): Asset {
        val asset = assetRepository.findById(assetId)
            .orElseThrow { NotFoundException() }
        asset.apply {
            name = dto.name
            defaults = dto.defaults
        }
        return assetRepository.save(asset)
    }

    fun deleteAsset(memberId: Long, assetId: Long) {
        val asset = assetRepository.findById(assetId)
            .orElseThrow { NotFoundException() }
        assetRepository.delete(asset)
    }
}