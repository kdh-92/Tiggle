package com.side.tiggle.domain.asset.dto;

import com.side.tiggle.domain.asset.model.Asset;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class AssetDto {

    private String name;
    private boolean defaults;

    public static AssetDto fromEntity(Asset asset) {
        return AssetDto.builder()
                .name(asset.getName())
                .defaults(asset.isDefaults())
                .build();
    }
}
