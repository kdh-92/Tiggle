package com.side.tiggle.domain.asset.api;

import com.side.tiggle.domain.asset.dto.AssetDto;
import com.side.tiggle.domain.asset.dto.resp.AssetRespDto;
import com.side.tiggle.domain.asset.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/asset")
public class AssetApiController {

    private final AssetService assetService;
    @PutMapping
    public ResponseEntity<AssetRespDto> createAsset(@RequestBody AssetDto assetDto) {
        return new ResponseEntity<>(AssetRespDto.fromEntity(assetService.createAsset(assetDto)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetRespDto> getAsset(@PathVariable("id") Long assetId) {
        return new ResponseEntity<>(AssetRespDto.fromEntity(assetService.getAsset(assetId)), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AssetRespDto>> getAllAsset() {
        return new ResponseEntity<>(assetService.getAllAsset()
                .stream()
                .map(AssetRespDto::fromEntity)
                .collect(Collectors.toList()), HttpStatus.OK);
    }
}
