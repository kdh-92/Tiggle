package com.side.tiggle.domain.character.api

import com.side.tiggle.domain.character.dto.resp.CharacterDetailRespDto
import com.side.tiggle.domain.character.model.CharacterCatalog
import com.side.tiggle.domain.character.repository.CharacterCatalogRepository
import com.side.tiggle.domain.character.service.CharacterService
import com.side.tiggle.global.common.ApiResponse
import com.side.tiggle.global.common.constants.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/v1/character")
class CharacterApiController(
    private val characterService: CharacterService,
    private val characterCatalogRepository: CharacterCatalogRepository
) {

    /**
     * 내 캐릭터 조회 (없으면 자동 생성)
     */
    @GetMapping("/me")
    fun getMyCharacter(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<ApiResponse<CharacterDetailRespDto>> {
        characterService.getOrCreateCharacter(memberId)
        val detail = characterService.getCharacterDetail(memberId)
        return ResponseEntity.ok(ApiResponse.success(detail))
    }

    /**
     * 다른 회원의 캐릭터 조회 (제한된 정보)
     */
    @GetMapping("/{memberId}")
    fun getCharacter(
        @PathVariable memberId: Long
    ): ResponseEntity<ApiResponse<CharacterDetailRespDto>> {
        val detail = characterService.getCharacterDetail(memberId)
        return ResponseEntity.ok(ApiResponse.success(detail))
    }

    /**
     * 캐릭터 카탈로그 전체 조회
     */
    @GetMapping("/catalog")
    fun getCatalog(): ResponseEntity<ApiResponse<List<CharacterCatalog>>> {
        val catalog = characterCatalogRepository.findAll()
        return ResponseEntity.ok(ApiResponse.success(catalog))
    }
}
