package com.side.tiggle.domain.tag.repository;

import com.side.tiggle.domain.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByDefaultsTrue();
}
