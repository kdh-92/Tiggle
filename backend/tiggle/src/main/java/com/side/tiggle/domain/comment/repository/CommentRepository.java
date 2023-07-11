package com.side.tiggle.domain.comment.repository;

import com.side.tiggle.domain.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
