package com.side.tiggle.domain.reaction.service;

import com.side.tiggle.domain.member.MemberDto;
import com.side.tiggle.domain.member.model.Member;
import com.side.tiggle.domain.reaction.ReactionDto;
import com.side.tiggle.domain.reaction.model.Reaction;
import com.side.tiggle.domain.reaction.repository.ReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 임시 CRUD (추가 작업 필요)
 */
@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionRepository reactionRepository;

    public ReactionDto createReaction(ReactionDto reactionDto) {
        Reaction reaction = Reaction.builder()
                .txId(reactionDto.getTxId())
                .senderId(reactionDto.getSenderId())
                .receiverId(reactionDto.getReceiverId())
                .type(reactionDto.getType())
                .build();

        return reactionDto.fromEntity(reactionRepository.save(reaction));
    }

    public ReactionDto getReaction(Long reactionId) {
        return ReactionDto.fromEntity(reactionRepository.findById(reactionId)
                .orElseThrow(() -> new RuntimeException("")));
    }

    public List<ReactionDto> getAllReaction() {
        List<ReactionDto> reactionDtoList = new ArrayList<>();

        for (Reaction reaction : reactionRepository.findAll()) {
            reactionDtoList.add(ReactionDto.fromEntity(reaction));
        }

        return reactionDtoList;
    }

    // update

    // delete
}

