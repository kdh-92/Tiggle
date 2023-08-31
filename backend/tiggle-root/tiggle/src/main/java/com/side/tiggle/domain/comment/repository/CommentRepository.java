package com.side.tiggle.domain.comment.repository;

import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.transaction.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByTxAndParentIdNull(Transaction tx, Pageable pageable);
    Page<Comment> findAllByParentId(Long parentId, Pageable pageable);
    int countAllByTxAndParentId(Transaction tx, Long parentId);
    int countAllByTx(Transaction tx);
}
