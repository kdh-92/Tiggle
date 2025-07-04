package com.side.tiggle.domain.tag.service

import com.side.tiggle.domain.tag.dto.req.TagCreateReqDto
import com.side.tiggle.domain.tag.dto.req.TagUpdateReqDto
import com.side.tiggle.domain.tag.dto.resp.TagRespDto
import com.side.tiggle.domain.tag.exception.TagException
import com.side.tiggle.domain.tag.exception.error.TagErrorCode
import com.side.tiggle.domain.tag.model.Tag
import com.side.tiggle.domain.tag.repository.TagRepository
import org.springframework.stereotype.Service

@Service
class TagServiceImpl(
    private val tagRepository: TagRepository
) : TagService {

    override fun createTag(createReqDto: TagCreateReqDto): TagRespDto {
        val tag = Tag(createReqDto.name)
        return TagRespDto.fromEntity(tagRepository.save(tag))
    }

    override fun getTag(tagId: Long): TagRespDto {
        val tag = tagRepository.findById(tagId)
            .orElseThrow { TagException(TagErrorCode.TAG_NOT_FOUND) }
        return TagRespDto.fromEntity(tag)
    }

    override fun getAllDefaultTag(): List<TagRespDto> {
        return tagRepository.findByDefaultsTrue()
            .map { TagRespDto.fromEntity(it) }
    }

    override fun updateTag(tagId: Long, updateReqDto: TagUpdateReqDto): TagRespDto {
        val tag = tagRepository.findById(tagId)
            .orElseThrow { TagException(TagErrorCode.TAG_NOT_FOUND) }
            .apply {
                name = updateReqDto.name
            }
        return TagRespDto.fromEntity(tagRepository.save(tag))
    }
}