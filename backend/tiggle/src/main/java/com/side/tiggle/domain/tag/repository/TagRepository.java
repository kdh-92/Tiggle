package com.side.tiggle.domain.tag.repository;

import com.side.tiggle.domain.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
