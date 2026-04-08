package com.side.tiggle.domain.character.util

import com.side.tiggle.domain.character.model.ColorRarity
import java.security.MessageDigest

/**
 * 회원 데이터를 기반으로 고유한 색상을 SHA-256 해시로 생성합니다.
 */
object ColorGenerator {

    fun generate(memberId: Long, email: String): MemberColor {
        val hash = sha256("${memberId}:${email}")
        val hue = (hash[0].toInt() and 0xFF) % 360
        val saturation = (hash[1].toInt() and 0xFF) % 41 + 50  // 50~90
        val lightness = (hash[2].toInt() and 0xFF) % 21 + 45   // 45~65

        val rarityByte = hash[4].toInt() and 0xFF
        val rarity = when {
            rarityByte < 2 -> ColorRarity.LEGENDARY     // ~0.78%
            rarityByte < 8 -> ColorRarity.HOLOGRAPHIC    // ~2.34%
            rarityByte < 26 -> ColorRarity.SHINE         // ~7.03%
            else -> ColorRarity.NORMAL                   // ~89.84%
        }

        return MemberColor(hue, saturation, lightness, rarity)
    }

    private fun sha256(input: String): ByteArray {
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest(input.toByteArray())
    }
}
