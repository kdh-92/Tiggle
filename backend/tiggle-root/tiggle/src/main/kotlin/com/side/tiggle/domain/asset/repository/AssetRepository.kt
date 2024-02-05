package com.side.tiggle.domain.asset.repository

import com.side.tiggle.domain.asset.model.Asset
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AssetRepository: JpaRepository<Asset, Long>