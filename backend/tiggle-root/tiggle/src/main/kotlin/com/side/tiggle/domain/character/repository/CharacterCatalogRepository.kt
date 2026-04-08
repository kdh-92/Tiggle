package com.side.tiggle.domain.character.repository

import com.side.tiggle.domain.character.model.CharacterCatalog
import com.side.tiggle.domain.character.model.CharacterPath
import com.side.tiggle.domain.character.model.CharacterTier
import org.springframework.data.jpa.repository.JpaRepository

interface CharacterCatalogRepository : JpaRepository<CharacterCatalog, Long> {
    fun findByPathAndLevel(path: CharacterPath, level: Int): CharacterCatalog?
    fun findAllByPath(path: CharacterPath): List<CharacterCatalog>
    fun findAllByTier(tier: CharacterTier): List<CharacterCatalog>
}
