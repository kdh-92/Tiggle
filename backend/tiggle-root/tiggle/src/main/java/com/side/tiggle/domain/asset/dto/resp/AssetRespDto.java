package com.side.tiggle.domain.asset.dto.resp;

import com.side.tiggle.domain.asset.dto.AssetDto;
import com.side.tiggle.domain.asset.model.Asset;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class AssetRespDto extends AssetDto {

    private Long id;

    public static AssetRespDto fromEntity(Asset asset) {
        return AssetRespDto.builder()
                .id(asset.getId())
                .name(asset.getName())
                .defaults(asset.isDefaults())
                .build();
    }

}
