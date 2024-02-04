package com.side.tiggle.domain.member.api;

import com.side.tiggle.domain.member.dto.MemberDto;
import com.side.tiggle.domain.member.service.MemberService;
import com.side.tiggle.global.common.constants.HttpHeaders;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 임시 CRUD (추가 작업 필요)
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMember(@PathVariable("id") Long memberId) {
        return new ResponseEntity<>(MemberDto.fromEntity(memberService.getMember(memberId)), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MemberDto>> getAllMember() {
        return new ResponseEntity<>(memberService.getAllMember(), HttpStatus.OK);
    }

    @GetMapping("/me")
    @Operation(summary = "내 정보 조회", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<MemberDto> getMe(
            @Parameter(hidden = true)
            @RequestHeader(name = HttpHeaders.MEMBER_ID) Long memberId
    ) {
        return new ResponseEntity<>(MemberDto.fromEntity(memberService.getMember(memberId)), HttpStatus.OK);
    }

    @PutMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "프로필 업데이트", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<MemberDto> updateMe(
            @Parameter(hidden = true)
            @RequestHeader(name = HttpHeaders.MEMBER_ID) Long memberId,
            @RequestPart MemberDto memberDto,
            @RequestPart(value = "multipartFile", required = false)MultipartFile file
            ) throws IOException {
        memberDto.setId(memberId);
        return new ResponseEntity<>(memberService.updateMember(memberId, memberDto, file), HttpStatus.OK);
    }
}
