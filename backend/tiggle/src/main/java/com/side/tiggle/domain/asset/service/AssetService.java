package com.side.tiggle.domain.asset.service;

import com.side.tiggle.domain.asset.model.Asset;
import com.side.tiggle.domain.asset.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;

    public Asset getAsset(Long assetId) {
        return assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException(""));
    }
}
