package com.side.tiggle.domain.tag.service

import com.side.tiggle.domain.tag.dto.req.TagCreateReqDto
import com.side.tiggle.domain.tag.dto.req.TagUpdateReqDto
import com.side.tiggle.domain.tag.dto.resp.TagRespDto

interface TagService {

    fun createTag(createReqDto: TagCreateReqDto): TagRespDto

    fun getTag(tagId: Long): TagRespDto

    fun getAllDefaultTag(): List<TagRespDto>

    fun updateTag(tagId: Long, updateReqDto: TagUpdateReqDto): TagRespDto
}