package com.side.tiggle.domain.comment.service;

import com.side.tiggle.domain.comment.CommentDto;
import com.side.tiggle.domain.comment.repository.CommentRepository;
import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 임시 CRUD (추가 작업 필요)
 */

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = Comment.builder()
                .txId(commentDto.getTxId())
                .parentId(commentDto.getParentId())
                .senderId(commentDto.getSenderId())
                .receiverId(commentDto.getReceiverId())
                .content(commentDto.getContent())
                .build();

        return CommentDto.fromEntity(commentRepository.save(comment));
    }

    public CommentDto getComment(Long commentId) {
        return CommentDto.fromEntity(commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("")));
    }

    public List<CommentDto> getAllComment() {
        List<CommentDto> CommentDtoList = new ArrayList<>();
        for (Comment Comment : commentRepository.findAll()) {
            CommentDtoList.add(CommentDto.fromEntity(Comment));
        }

        return CommentDtoList;
    }

    public CommentDto updateComment(Long id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        comment.setContent(commentDto.getContent());

        return CommentDto.fromEntity(commentRepository.save(comment));
    }

    // delete
}

