package com.side.tiggle.domain.grade.repository;

import com.side.tiggle.domain.grade.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
