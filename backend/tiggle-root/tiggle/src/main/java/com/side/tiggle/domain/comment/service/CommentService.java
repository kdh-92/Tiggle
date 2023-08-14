package com.side.tiggle.domain.comment.service;

import com.side.tiggle.domain.comment.repository.CommentRepository;
import com.side.tiggle.domain.comment.dto.req.CommentCreateReqDto;
import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.member.model.Member;
import com.side.tiggle.domain.member.repository.MemberRepository;
import com.side.tiggle.domain.transaction.model.Transaction;
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
    private final TransactionService transactionService;

    public Comment getById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(""));
    }

    public int getParentCount(long txId) {
        Transaction transaction = this.transactionService.getTransaction(txId);
        return commentRepository.countAllByTxAndParentId(transaction, null);
    }

    public int getChildCount(long txId, long parentId) {
        Transaction transaction = this.transactionService.getTransaction(txId);
        return commentRepository.countAllByTxAndParentId(transaction, parentId);
    }

    public Page<Comment> getParentsByTxId(Long txId, int page, int size){
        Transaction tx = transactionService.getTransaction(txId);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        return commentRepository.findAllByTxAndParentIdNull(tx, pageable);
    }

    public Page<Comment> getChildrenByParentId(Long parentId, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        return commentRepository.findAllByParentId(parentId, pageable);
    }

    public Comment createComment(CommentCreateReqDto commentDto) {
        // TODO : Service 메소드로 수정한다
        Transaction tx = transactionService.getTransaction(commentDto.getTxId());
        Member sender = memberRepository.findById(commentDto.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 요청입니다"));

        assertValid(commentDto);
        Comment comment = commentDto.toEntity(tx, sender);
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long memberId, Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId).stream()
                .filter( it -> it.getSender().getId().equals(memberId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        comment.setContent(content);
        commentRepository.save(comment);
        return comment;
    }

    public void deleteComment(Long memberId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).stream()
                .filter( it -> it.getSender().getId().equals(memberId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다"));
        commentRepository.delete(comment);
    }

    private void assertValid(CommentCreateReqDto dto) {
        if (dto.getParentId() != null) {
            commentRepository.findById(dto.getParentId())
                    .orElseThrow(()-> new IllegalArgumentException("유효하지 않은 요청입니다"));
        }
        memberRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 요청입니다"));
        memberRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 요청입니다"));
    }

}

