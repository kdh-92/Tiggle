package com.side.tiggle.domain.member.api

import com.side.tiggle.domain.member.dto.req.MemberCreateReqDto
import com.side.tiggle.domain.member.dto.req.MemberUpdateReqDto
import com.side.tiggle.domain.member.dto.resp.MemberListRespDto
import com.side.tiggle.domain.member.dto.resp.MemberRespDto
import com.side.tiggle.domain.member.service.MemberService
import com.side.tiggle.global.common.ApiResponse
import com.side.tiggle.global.common.constants.HttpHeaders
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Validated
@RestController
@RequestMapping("/api/v1/member")
class MemberApiController(
    private val memberService: MemberService
) {

    @PostMapping
    fun createMember(
        @RequestBody @Valid memberCreateReqDto: MemberCreateReqDto
    ): ResponseEntity<ApiResponse<MemberRespDto>> {
        val respDto = memberService.createMember(memberCreateReqDto)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(respDto))
    }

    @GetMapping("/{id}")
    fun getMember(
        @PathVariable("id") @Min(1) memberId: Long
    ): ResponseEntity<ApiResponse<MemberRespDto>> {
        val memberDto = memberService.getMember(memberId)
        return ResponseEntity
            .ok(ApiResponse.success(memberDto))
    }

    // 관리자 roles이 있는지 확인 필요
    @GetMapping("/all")
    fun getAllMember(): ResponseEntity<ApiResponse<MemberListRespDto>> {
        val members = memberService.getAllMember()
        return ResponseEntity
            .ok(ApiResponse.success(members))
    }

    // token에 id 값 있는지 확인 필요
    @GetMapping("/me")
    @Operation(summary = "내 정보 조회", security = [SecurityRequirement(name = "bearer-key")])
    fun getMe(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<ApiResponse<MemberRespDto>> {
        val memberDto = memberService.getMember(memberId)
        return ResponseEntity
            .ok(ApiResponse.success(memberDto))
    }

    @PutMapping("/me", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "프로필 업데이트", security = [SecurityRequirement(name = "bearer-key")])
    fun updateMe(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @RequestPart(required = false) @Valid dto: MemberUpdateReqDto,
        @RequestPart("multipartFile", required = false) file: MultipartFile?
    ): ResponseEntity<ApiResponse<MemberRespDto>> {
        val updatedMember = memberService.updateMember(memberId, dto, file)
        return ResponseEntity
            .ok(ApiResponse.success(updatedMember))
    }

//    @DeleteMapping("/{id}")
}
