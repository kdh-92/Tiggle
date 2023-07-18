package com.side.tiggle.domain.comment.service;

import com.side.tiggle.domain.comment.dto.CommentDto;
import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.comment.repository.CommentRepository;
import com.side.tiggle.domain.member.repository.MemberRepository;
import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.domain.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 임시 CRUD (추가 작업 필요)
 */

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final TransactionRepository transactionRepository;

    public Comment createComment(CommentDto commentDto) {
        Transaction tx = transactionRepository.findById(commentDto.getTxId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 요청입니다"));

        assertValid(commentDto);
        Comment comment = commentDto.toEntity(tx);
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long memberId, Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId).stream().filter( it -> it.getSenderId().equals(memberId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        comment.setContent(content);
        commentRepository.save(comment);
        return comment;
    }

    public void deleteComment(Long memberId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).stream().filter( it -> it.getSenderId().equals(memberId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다"));
        commentRepository.delete(comment);
    }

    private void assertValid(CommentDto dto) {
        if (dto.getParentId() != null) {
            commentRepository.findById(dto.getParentId()).orElseThrow(()-> new IllegalArgumentException("유효하지 않은 요청입니다"));
        }
        memberRepository.findById(dto.getSenderId()).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 요청입니다"));
        memberRepository.findById(dto.getReceiverId()).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 요청입니다"));
    }

}

