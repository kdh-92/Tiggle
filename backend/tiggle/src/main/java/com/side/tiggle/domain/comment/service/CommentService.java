package com.side.tiggle.domain.comment.service;

import com.side.tiggle.domain.comment.CommentDto;
import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.comment.repository.CommentRepository;
import com.side.tiggle.domain.member.repository.MemberRepository;
import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.domain.transaction.repository.TransactionRepository;
import com.side.tiggle.domain.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final TransactionRepository transactionRepository;

    public Page<Comment> getParentsByTxId(Long txId, int page, int size){
        Transaction tx = transactionRepository.findById(txId)
                .orElseThrow(()-> new IllegalArgumentException("거래를 찾을 수 없습니다"));
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        return commentRepository.findAllByTxAndParentIdNull(tx, pageable);
    }

    public Page<Comment> getChildrenByParentId(Long parentId, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        return commentRepository.findAllByParentId(parentId, pageable);
    }

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

