package com.side.tiggle.domain.character.util

import com.side.tiggle.domain.character.model.ColorRarity
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.doubles.shouldBeGreaterThanOrEqual
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ColorGeneratorTest : StringSpec({

    "같은 입력에 대해 항상 같은 결과를 반환한다" {
        val color1 = ColorGenerator.generate(1L, "test@example.com")
        val color2 = ColorGenerator.generate(1L, "test@example.com")

        color1 shouldBe color2
    }

    "다른 memberId에 대해 다른 색상을 생성한다" {
        val color1 = ColorGenerator.generate(1L, "test@example.com")
        val color2 = ColorGenerator.generate(2L, "test@example.com")

        color1 shouldNotBe color2
    }

    "다른 이메일에 대해 다른 색상을 생성한다" {
        val color1 = ColorGenerator.generate(1L, "a@example.com")
        val color2 = ColorGenerator.generate(1L, "b@example.com")

        color1 shouldNotBe color2
    }

    "hue 값은 0~359 범위이다" {
        val color = ColorGenerator.generate(1L, "test@example.com")

        color.hue shouldBeGreaterThanOrEqual 0
        color.hue shouldBeLessThan 360
    }

    "saturation 값은 50~90 범위이다" {
        val color = ColorGenerator.generate(1L, "test@example.com")

        color.saturation shouldBeGreaterThanOrEqual 50
        color.saturation shouldBeLessThanOrEqual 90
    }

    "lightness 값은 45~65 범위이다" {
        val color = ColorGenerator.generate(1L, "test@example.com")

        color.lightness shouldBeGreaterThanOrEqual 45
        color.lightness shouldBeLessThanOrEqual 65
    }

    "rarity는 유효한 ColorRarity 값이다" {
        val color = ColorGenerator.generate(1L, "test@example.com")

        color.rarity shouldNotBe null
        ColorRarity.entries.contains(color.rarity) shouldBe true
    }

    "다양한 입력에 대해 값 범위를 준수한다" {
        for (i in 1L..100L) {
            val color = ColorGenerator.generate(i, "user${i}@example.com")

            color.hue shouldBeGreaterThanOrEqual 0
            color.hue shouldBeLessThan 360
            color.saturation shouldBeGreaterThanOrEqual 50
            color.saturation shouldBeLessThanOrEqual 90
            color.lightness shouldBeGreaterThanOrEqual 45
            color.lightness shouldBeLessThanOrEqual 65
        }
    }

    "NORMAL rarity가 가장 빈번하게 발생한다" {
        var normalCount = 0
        val totalSamples = 1000

        for (i in 1L..totalSamples) {
            val color = ColorGenerator.generate(i, "rarity_test_${i}@example.com")
            if (color.rarity == ColorRarity.NORMAL) normalCount++
        }

        // NORMAL should be at least 80% (threshold is ~90%)
        (normalCount.toDouble() / totalSamples) shouldBeGreaterThanOrEqual 0.80
    }
})
