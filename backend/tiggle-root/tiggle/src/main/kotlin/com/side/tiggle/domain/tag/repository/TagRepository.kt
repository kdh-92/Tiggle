package com.side.tiggle.domain.tag.repository

import com.side.tiggle.domain.tag.model.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository: JpaRepository<Tag, Long> {
    fun findByDefaultsTrue(): List<Tag>
}