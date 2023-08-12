package com.side.tiggle.domain.grade;

import com.side.tiggle.domain.grade.model.Grade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeDto {
    long id;
    String name;
    String imageUrl;
    String description;

    public static GradeDto fromEntity(Grade grade) {
        GradeDto dto = new GradeDto();
        dto.id = grade.getId();
        dto.name = grade.getName();
        dto.imageUrl = grade.getImageUrl();
        dto.description = grade.getDescription();

        return dto;
    }
}
