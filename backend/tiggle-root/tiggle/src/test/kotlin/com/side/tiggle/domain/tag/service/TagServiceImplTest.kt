package com.side.tiggle.domain.tag.service

import com.side.tiggle.domain.tag.dto.req.TagCreateReqDto
import com.side.tiggle.domain.tag.dto.req.TagUpdateReqDto
import com.side.tiggle.domain.tag.dto.resp.TagRespDto
import com.side.tiggle.domain.tag.exception.TagException
import com.side.tiggle.domain.tag.exception.error.TagErrorCode
import com.side.tiggle.domain.tag.model.Tag
import com.side.tiggle.domain.tag.repository.TagRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import java.util.*

class TagServiceImplTest : StringSpec({

    val tagRepository: TagRepository = mockk()
    val tagService: TagService = TagServiceImpl(tagRepository)

    beforeEach {
        clearAllMocks()
    }

    "태그를 생성합니다" {
        // given
        val dto = TagCreateReqDto(name = "식비")
        val expected = Tag(name = dto.name)

        val capturedSlot = slot<Tag>()
        every { tagRepository.save(capture(capturedSlot)) } returns expected

        // when
        tagService.createTag(dto)

        // then
        verify(exactly = 1) { tagRepository.save(any()) }
        capturedSlot.captured.name shouldBe "식비"
        capturedSlot.captured.defaults shouldBe false
    }

    "태그 ID로 조회 시 존재하면 반환합니다" {
        // given
        val tagId = 1L
        val tag = Tag(name = "교통비").apply { id = tagId }
        val expectedDto = TagRespDto.fromEntity(tag)

        every { tagRepository.findById(tagId) } returns Optional.of(tag)

        // when
        val result = tagService.getTag(tagId)

        // then
        result shouldBe expectedDto
        result.id shouldBe tagId
        result.name shouldBe "교통비"
    }

    "태그 ID로 조회 시 존재하지 않으면 예외를 던집니다" {
        // given
        val tagId = 999L

        every { tagRepository.findById(tagId) } returns Optional.empty()

        // when & then
        shouldThrow<TagException> {
            tagService.getTag(tagId)
        }.getErrorCode() shouldBe TagErrorCode.TAG_NOT_FOUND
    }

    "모든 기본 태그 목록을 조회합니다" {
        // given
        val defaultTags = listOf(
            Tag("식비", defaults = true).apply { id = 1L },
            Tag("교통비", defaults = true).apply { id = 2L },
            Tag("문화생활", defaults = true).apply { id = 3L }
        )

        every { tagRepository.findByDefaultsTrue() } returns defaultTags

        // when
        val result = tagService.getAllDefaultTag()

        // then
        result.size shouldBe 3
        result[0].name shouldBe "식비"
        result[1].name shouldBe "교통비"
        result[2].name shouldBe "문화생활"
        verify(exactly = 1) { tagRepository.findByDefaultsTrue() }
    }

    "기본 태그가 없으면 빈 목록을 반환합니다" {
        // given
        every { tagRepository.findByDefaultsTrue() } returns emptyList()

        // when
        val result = tagService.getAllDefaultTag()

        // then
        result.size shouldBe 0
        verify(exactly = 1) { tagRepository.findByDefaultsTrue() }
    }

    "태그 이름을 수정합니다" {
        // given
        val tagId = 1L
        val dto = TagUpdateReqDto(name = "수정된태그명")
        val tag = Tag("기존태그명").apply { id = tagId }

        every { tagRepository.findById(tagId) } returns Optional.of(tag)
        every { tagRepository.save(tag) } returns tag

        // when
        tagService.updateTag(tagId, dto)

        // then
        tag.name shouldBe "수정된태그명"
        verify(exactly = 1) { tagRepository.findById(tagId) }
        verify(exactly = 1) { tagRepository.save(tag) }
    }

    "태그 수정 시 존재하지 않는 ID라면 예외를 던집니다" {
        // given
        val tagId = 999L
        val dto = TagUpdateReqDto(name = "수정된태그명")

        every { tagRepository.findById(tagId) } returns Optional.empty()

        // when & then
        shouldThrow<TagException> {
            tagService.updateTag(tagId, dto)
        }.getErrorCode() shouldBe TagErrorCode.TAG_NOT_FOUND
    }

    "태그 생성 시 기본값으로 defaults가 false로 설정됩니다" {
        // given
        val dto = TagCreateReqDto(name = "사용자정의태그")
        val tag = Tag(name = dto.name)

        val capturedSlot = slot<Tag>()
        every { tagRepository.save(capture(capturedSlot)) } returns tag

        // when
        tagService.createTag(dto)

        // then
        capturedSlot.captured.defaults shouldBe false
        capturedSlot.captured.member shouldBe null
    }

    "여러 개의 기본 태그를 정확한 순서로 반환합니다" {
        // given
        val defaultTags = listOf(
            Tag("A태그", defaults = true).apply { id = 1L },
            Tag("B태그", defaults = true).apply { id = 2L },
            Tag("C태그", defaults = true).apply { id = 3L }
        )

        every { tagRepository.findByDefaultsTrue() } returns defaultTags

        // when
        val result = tagService.getAllDefaultTag()

        // then
        result.size shouldBe 3
        result.forEachIndexed { index, tagRespDto ->
            tagRespDto.id shouldBe defaultTags[index].id
            tagRespDto.name shouldBe defaultTags[index].name
        }
    }
})