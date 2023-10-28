package com.side.tiggle.domain.reaction.service;

import com.side.tiggle.domain.member.model.Member;
import com.side.tiggle.domain.member.service.MemberService;
import com.side.tiggle.domain.reaction.dto.req.ReactionCreateDto;
import com.side.tiggle.domain.reaction.model.Reaction;
import com.side.tiggle.domain.reaction.model.ReactionType;
import com.side.tiggle.domain.reaction.repository.ReactionRepository;
import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.domain.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final TransactionService transactionService;
    private final MemberService memberService;

    public Reaction getReaction(long txId, long senderId) {
        return reactionRepository.findByTxIdAndSenderId(txId, senderId);
    }

    public int getReactionCount(long txId, ReactionType type){
        return reactionRepository.countAllByTxIdAndType(txId, type);
    }

    public Reaction upsertReaction(long txId, long senderId, ReactionCreateDto reactionDto) {
        Transaction transaction = transactionService.getTransaction(txId); // transaction 유효성 확인을 위해 한번 조회
        Reaction reaction = reactionRepository.findByTxIdAndSenderId(txId, senderId);

        if (reaction == null) {
            Member sender = memberService.getMember(senderId);
            Member receiver = memberService.getMember(reactionDto.getReceiverId());
            reaction = reactionDto.toEntity(transaction, sender, receiver);
        }

        reaction.setType(reactionDto.getType());
        reactionRepository.save(reaction);
        return reaction;
    }

    @Transactional
    public void deleteReaction(long txId, long senderId) {
        reactionRepository.deleteByTxIdAndSenderId(txId, senderId);
    }
}

