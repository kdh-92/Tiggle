package com.side.tiggle.domain.reaction.api;

import com.side.tiggle.domain.member.MemberDto;
import com.side.tiggle.domain.reaction.ReactionDto;
import com.side.tiggle.domain.reaction.service.ReactionService;
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
@RequestMapping("/reaction")
public class ReactionApiController {

    private final ReactionService reactionService;

    @PostMapping
    @Transactional
    public ResponseEntity<ReactionDto> createMember(@RequestBody ReactionDto reactionDto) {
        return new ResponseEntity<>(reactionService.createReaction(reactionDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReactionDto> getMember(@PathVariable("id") Long reactionId) {
        return new ResponseEntity<>(reactionService.getReaction(reactionId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReactionDto>> getAllMember() {
        return new ResponseEntity<>(reactionService.getAllReaction(), HttpStatus.OK);
    }

    // update

    // delete

}
