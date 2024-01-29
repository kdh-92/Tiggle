package com.side.tiggle.domain.tag.service

import com.side.tiggle.domain.tag.dto.TagDto
import com.side.tiggle.domain.tag.model.Tag
import com.side.tiggle.domain.tag.repository.TagRepository
import com.side.tiggle.global.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class TagService(
    private val tagRepository: TagRepository
) {

    fun createTag(tagDto: TagDto): TagDto {
        val tag = Tag(tagDto.name)
        return TagDto.fromEntity(tagRepository.save(tag))
    }

    fun getTag(tagId: Long): TagDto {
        val tag = tagRepository.findById(tagId).orElseThrow {
            NotFoundException()
        }
        return TagDto.fromEntity(tag)
    }

    fun getAllTag(): List<Tag> {
        return tagRepository.findAll()
    }

    fun getAllDefaultTag(): List<Tag> {
        return tagRepository.findByDefaultsTrue()
    }

    fun updateTag(tagId: Long, tagDto: TagDto): TagDto {
        val tag = tagRepository.findById(tagId)
            .orElseThrow { NotFoundException() }
            .apply {
                name = tagDto.name
            }
        return TagDto.fromEntity(tagRepository.save(tag))
    }
}