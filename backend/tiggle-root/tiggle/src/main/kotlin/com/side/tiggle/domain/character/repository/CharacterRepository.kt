package com.side.tiggle.domain.character.repository

import com.side.tiggle.domain.character.model.Character
import org.springframework.data.jpa.repository.JpaRepository

interface CharacterRepository : JpaRepository<Character, Long> {
    fun findByMemberId(memberId: Long): Character?
}
