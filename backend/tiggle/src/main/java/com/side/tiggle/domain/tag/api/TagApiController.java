package com.side.tiggle.domain.tag.api;

import com.side.tiggle.domain.tag.dto.TagDto;
import com.side.tiggle.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tag")
public class TagApiController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagDto> createTag(@RequestBody TagDto tagDto) {
        return new ResponseEntity<>(tagService.createTag(tagDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTag(@PathVariable("id") Long tagId) {
        return new ResponseEntity<>(tagService.getTag(tagId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TagDto>> getAllDefaultTag() {
        return new ResponseEntity<>(
                tagService.getAllDefaultTag()
                        .stream()
                        .map(TagDto::fromEntity)
                        .collect(Collectors.toList()
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDto> updateTag(@PathVariable("id") Long tagId,
                                            @RequestBody TagDto tagDto) {
        return new ResponseEntity<>(tagService.updateTag(tagId, tagDto), HttpStatus.OK);
    }

    // delete

}
