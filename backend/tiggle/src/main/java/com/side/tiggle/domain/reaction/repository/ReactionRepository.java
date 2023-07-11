package com.side.tiggle.domain.reaction.repository;

import com.side.tiggle.domain.reaction.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
}
