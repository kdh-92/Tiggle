package com.side.tiggle.domain.member.api

import com.side.tiggle.domain.member.dto.controller.MemberRequestDto
import com.side.tiggle.domain.member.dto.controller.MemberResponseDto
import com.side.tiggle.domain.member.dto.service.MemberDto
import com.side.tiggle.domain.member.service.MemberService
import com.side.tiggle.global.common.constants.HttpHeaders
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/member")
class MemberApiController(
    private val memberService: MemberService
) {

    @PostMapping
    fun createMember(
        @RequestBody memberRequestDto: MemberRequestDto
    ): ResponseEntity<MemberResponseDto> {
        return ResponseEntity(
            MemberDto.fromEntityToMemberResponseDto(memberService.createMember(MemberRequestDto.fromMemberRequestDtoToMemberDto(memberRequestDto))),
            HttpStatus.CREATED
        )
    }

    @GetMapping("/{id}")
    fun getMember(
        @PathVariable("id") memberId: Long
    ): ResponseEntity<MemberResponseDto> {
        return ResponseEntity(
            MemberDto.fromEntityToMemberResponseDto(memberService.getMember(memberId)),
            HttpStatus.OK
        )
    }

    // 관리자 roles이 있는지 확인 필요
    @GetMapping("/all")
    fun getAllMember(): ResponseEntity<List<MemberResponseDto>> {
        return ResponseEntity(
            memberService.getAllMember()
                .map { MemberDto.fromEntityToMemberResponseDto(it) },
            HttpStatus.OK
        )
    }

    // token에 id 값 있는지 확인 필요
    @GetMapping("/me")
    @Operation(summary = "내 정보 조회", security = [SecurityRequirement(name = "bearer-key")])
    fun getMe(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<MemberResponseDto> {
        return ResponseEntity(
            MemberDto.fromEntityToMemberResponseDto(memberService.getMember(memberId)),
            HttpStatus.OK
        )
    }

    @PutMapping("/me", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "프로필 업데이트", security = [SecurityRequirement(name = "bearer-key")])
    fun updateMe(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @RequestPart memberRequestDto: MemberRequestDto,
        @RequestPart("multipartFile") file: MultipartFile?
    ): ResponseEntity<MemberResponseDto> {
        return ResponseEntity(
            MemberDto.fromEntityToMemberResponseDto(memberService.updateMember(memberId, MemberRequestDto.fromMemberRequestDtoToMemberDto(memberRequestDto), file)),
            HttpStatus.OK
        )
    }

//    @DeleteMapping("/{id}")
}
