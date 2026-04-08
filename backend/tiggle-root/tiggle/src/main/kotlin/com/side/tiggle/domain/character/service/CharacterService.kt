package com.side.tiggle.domain.character.service

import com.side.tiggle.domain.character.dto.resp.CharacterDetailRespDto
import com.side.tiggle.domain.character.model.Character

interface CharacterService {

    fun getOrCreateCharacter(memberId: Long): Character

    fun getCharacterDetail(memberId: Long): CharacterDetailRespDto

    fun addExperience(memberId: Long, amount: Int, reason: String)

    fun incrementEggRecords(memberId: Long)

    fun checkAndHatch(memberId: Long): Boolean
}
