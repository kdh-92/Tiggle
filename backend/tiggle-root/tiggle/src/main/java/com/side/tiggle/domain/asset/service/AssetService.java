package com.side.tiggle.domain.asset.service;

import com.side.tiggle.domain.asset.dto.AssetDto;
import com.side.tiggle.domain.asset.repository.AssetRepository;
import com.side.tiggle.domain.asset.model.Asset;
import com.side.tiggle.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;

    public Asset createAsset(AssetDto dto) {
        Asset asset = Asset.builder()
                .name(dto.getName())
                .defaults(dto.isDefaults())
                .build();

        return assetRepository.save(asset);
    }

    public Asset getAsset(Long assetId) {
        return assetRepository.findById(assetId)
                .orElseThrow(() -> new NotFoundException());
    }

    public List<Asset> getAllAsset() {
        return assetRepository.findAll();
    }
}
