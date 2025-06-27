package com.side.tiggle.domain.tag.service

import com.side.tiggle.domain.tag.dto.req.TagCreateReqDto
import com.side.tiggle.domain.tag.dto.req.TagUpdateReqDto
import com.side.tiggle.domain.tag.dto.resp.TagRespDto
import com.side.tiggle.domain.tag.model.Tag
import com.side.tiggle.domain.tag.repository.TagRepository
import org.springframework.stereotype.Service

@Service
class TagService(
    private val tagRepository: TagRepository
) {

    fun createTag(createReqDto: TagCreateReqDto): TagRespDto {
        val tag = Tag(createReqDto.name)
        return TagRespDto.fromEntity(tagRepository.save(tag))
    }

    fun getTag(tagId: Long): TagRespDto {
        val tag = tagRepository.findById(tagId).orElseThrow {
            NotFoundException()
        }
        return TagRespDto.fromEntity(tag)
    }

    fun getAllDefaultTag(): List<TagRespDto> {
        return tagRepository.findByDefaultsTrue()
            .map { TagRespDto.fromEntity(it) }
    }

    fun updateTag(tagId: Long, updateReqDto: TagUpdateReqDto): TagRespDto {
        val tag = tagRepository.findById(tagId)
            .orElseThrow { NotFoundException() }
            .apply {
                name = updateReqDto.name
            }
        return TagRespDto.fromEntity(tagRepository.save(tag))
    }
}
