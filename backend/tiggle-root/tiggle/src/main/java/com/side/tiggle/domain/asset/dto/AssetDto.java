package com.side.tiggle.domain.asset.dto;

import com.side.tiggle.domain.asset.model.Asset;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AssetDto {

    private Long id;
    private String name;

    public static AssetDto fromEntity(Asset asset) {
        return AssetDto.builder()
                .id(asset.getId())
                .name(asset.getName())
                .build();
    }
}
