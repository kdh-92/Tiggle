package com.side.tiggle.domain.reaction.repository;

import com.side.tiggle.domain.reaction.model.Reaction;
import com.side.tiggle.domain.reaction.model.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    Reaction findByTxIdAndSenderId(long txId, long senderId);
    int countAllByTxIdAndType(long txId, ReactionType type);
    int deleteByTxIdAndSenderId(long txId, long senderId);

}
