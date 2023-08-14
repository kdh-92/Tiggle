package com.side.tiggle.domain.grade.service;

import com.side.tiggle.domain.grade.repository.GradeRepository;
import com.side.tiggle.domain.grade.GradeDto;
import com.side.tiggle.domain.grade.model.Grade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;

    public GradeDto createGrade(GradeDto gradeDto) {
        Grade grade = Grade.builder()
                .name(gradeDto.getName())
                .imageUrl(gradeDto.getImageUrl())
                .description(gradeDto.getDescription())
                .build();

        return gradeDto.fromEntity(gradeRepository.save(grade));
    }

    public GradeDto getGrade(Long gradeId) {
        return GradeDto.fromEntity(gradeRepository.findById(gradeId)
                .orElseThrow(() -> new RuntimeException("")));
    }

    public List<GradeDto> getAllGrade() {
        List<GradeDto> gradeDtoList = new ArrayList<>();

        for (Grade grade : gradeRepository.findAll()) {
            gradeDtoList.add(GradeDto.fromEntity(grade));
        }

        // return gradeRepository.findAll().stream().map(grade -> GradeDto.fromEntity(grade)).collect(Collectors.toList());
        return gradeDtoList;
    }

    public GradeDto updateGrade(Long gradeId, GradeDto gradeDto) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 등급이 존재하지 않습니다."));

        grade.setName(gradeDto.getName());
        grade.setImageUrl(gradeDto.getImageUrl());
        grade.setDescription(gradeDto.getDescription());

        return gradeDto.fromEntity(gradeRepository.save(grade));
    }

    // delete
}

