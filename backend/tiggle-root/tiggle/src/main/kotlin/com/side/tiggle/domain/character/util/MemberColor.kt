package com.side.tiggle.domain.character.util

import com.side.tiggle.domain.character.model.ColorRarity

data class MemberColor(
    val hue: Int,
    val saturation: Int,
    val lightness: Int,
    val rarity: ColorRarity
)
