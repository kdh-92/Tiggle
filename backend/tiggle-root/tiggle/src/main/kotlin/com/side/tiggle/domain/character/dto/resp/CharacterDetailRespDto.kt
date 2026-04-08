package com.side.tiggle.domain.character.dto.resp

import com.side.tiggle.domain.character.model.CharacterPath
import com.side.tiggle.domain.character.model.CharacterStage
import com.side.tiggle.domain.character.model.CharacterTier
import com.side.tiggle.domain.character.model.ColorRarity

data class CharacterDetailRespDto(
    val stage: CharacterStage,
    val tier: CharacterTier?,
    val path: CharacterPath?,
    val level: Int,
    val experience: Int,
    val nextLevelExp: Int?,
    val currentForm: CharacterFormDto?,
    val color: ColorRespDto,
    val egg: EggStatusDto?
)

data class CharacterFormDto(
    val name: String,
    val nameEn: String,
    val description: String?,
    val imageKey: String
)

data class ColorRespDto(
    val hue: Int,
    val saturation: Int,
    val lightness: Int,
    val rarity: ColorRarity,
    val cssValue: String
)

data class EggStatusDto(
    val phase: Int,
    val records: Int,
    val nextPhaseAt: Int
)
