package com.side.tiggle.domain.tag.service;

import com.side.tiggle.domain.tag.model.Tag;
import com.side.tiggle.domain.tag.repository.TagRepository;
import com.side.tiggle.domain.tag.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public TagDto createTag(TagDto tagDto) {
        Tag tag = Tag.builder()
                .name(tagDto.getName())
                .build();

        return tagDto.fromEntity(tagRepository.save(tag));
    }

    public TagDto getTag(Long tagId) {
        return TagDto.fromEntity(tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("")));
    }

    public List<Tag> getAllTag() {
        return tagRepository.findAll();
    }

    public List<Tag> getAllDefaultTag() {
        return tagRepository.findByDefaultsTrue();
    }

    public TagDto updateTag(Long tagId, TagDto tagDto) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("해당 태그가 존재하지 않습니다."));

        tag.setName(tagDto.getName());

        return tagDto.fromEntity(tagRepository.save(tag));
    }

    // delete
}

