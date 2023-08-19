package com.side.tiggle.domain.grade.api;

import com.side.tiggle.domain.grade.service.GradeService;
import com.side.tiggle.domain.grade.GradeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/grade")
public class GradeApiController {

    private final GradeService gradeService;

    @PostMapping
    public ResponseEntity<GradeDto> createGrade(@RequestBody GradeDto gradeDto) {
        return new ResponseEntity<>(gradeService.createGrade(gradeDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeDto> getGrade(@PathVariable("id") Long gradeId) {
        return new ResponseEntity<>(gradeService.getGrade(gradeId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GradeDto>> getAllGrade() {
        return new ResponseEntity<>(gradeService.getAllGrade(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeDto> updateGrade(@PathVariable("id") Long gradeId,
                                                @RequestBody GradeDto gradeDto) {
        return new ResponseEntity<>(gradeService.updateGrade(gradeId, gradeDto), HttpStatus.OK);
    }

    // delete

}
