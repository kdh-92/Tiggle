package com.side.tiggle.domain.member.api;

import com.side.tiggle.domain.member.dto.MemberDto;
import com.side.tiggle.domain.member.service.MemberService;
import com.side.tiggle.global.common.constants.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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

    @PostMapping
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberDto memberDto) {
        return new ResponseEntity<>(memberService.createMember(memberDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMember(@PathVariable("id") Long memberId) {
        return new ResponseEntity<>(MemberDto.fromEntity(memberService.getMember(memberId)), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MemberDto>> getAllMember() {
        return new ResponseEntity<>(memberService.getAllMember(), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDto> getMe(
            @RequestHeader(HttpHeaders.MEMBER_ID) Long memberId
    ) {
        return new ResponseEntity<>(MemberDto.fromEntity(memberService.getMember(memberId)), HttpStatus.OK);
    }

    @PutMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDto> updateMe(
            @RequestHeader(HttpHeaders.MEMBER_ID) Long memberId,
            @RequestPart MemberDto memberDto,
            @RequestPart("multipartFile")MultipartFile file
            ) throws IOException {
        return new ResponseEntity<>(memberService.updateMember(memberId, memberDto, file), HttpStatus.OK);
    }
}
