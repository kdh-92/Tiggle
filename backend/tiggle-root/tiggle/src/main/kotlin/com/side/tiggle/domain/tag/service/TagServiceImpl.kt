package com.side.tiggle.domain.tag.service

import com.side.tiggle.domain.tag.dto.req.TagCreateReqDto
import com.side.tiggle.domain.tag.dto.req.TagUpdateReqDto
import com.side.tiggle.domain.tag.dto.resp.TagRespDto
import com.side.tiggle.domain.tag.exception.TagException
import com.side.tiggle.domain.tag.exception.error.TagErrorCode
import com.side.tiggle.domain.tag.model.Tag
import com.side.tiggle.domain.tag.repository.TagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TagServiceImpl(
    private val tagRepository: TagRepository
) : TagService {

    @Transactional
    override fun createTag(createReqDto: TagCreateReqDto) {
        val tag = Tag(createReqDto.name)
        tagRepository.save(tag)
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

    @Transactional
    override fun updateTag(tagId: Long, updateReqDto: TagUpdateReqDto) {
        val tag = tagRepository.findById(tagId)
            .orElseThrow { TagException(TagErrorCode.TAG_NOT_FOUND) }
            .apply {
                name = updateReqDto.name
            }

        tagRepository.save(tag)
    }
}
