package com.side.tiggle.domain.member.api;

import com.side.tiggle.domain.member.MemberDto;
import com.side.tiggle.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(memberService.getMember(memberId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MemberDto>> getAllMember() {
        return new ResponseEntity<>(memberService.getAllMember(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable("id") Long memberId,
                                                  @RequestBody MemberDto memberDto) {
        return new ResponseEntity<>(memberService.updateMember(memberId, memberDto), HttpStatus.OK);
    }

    // delete
}
