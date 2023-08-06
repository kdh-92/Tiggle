package com.side.tiggle.domain.tag;

import com.side.tiggle.domain.tag.model.Tag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDto {
    long id;
    String name;

    public static TagDto fromEntity(Tag tag) {
        TagDto dto = new TagDto();
        dto.id = tag.getId();
        dto.name = tag.getName();

        return dto;
    }
}
