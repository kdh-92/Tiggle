package com.side.tiggle.domain.grade.api

import com.side.tiggle.domain.grade.GradeDto
import com.side.tiggle.domain.grade.service.GradeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/grade")
class GradeController(
    private val gradeService: GradeService
) {
    @PostMapping
    fun createGrade(@RequestBody gradeDto: GradeDto): ResponseEntity<GradeDto> {
        return ResponseEntity(gradeService.createGrade(gradeDto), HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getGrade(@PathVariable("id") gradeId: Long): ResponseEntity<GradeDto> {
        return ResponseEntity(gradeService.getGrade(gradeId), HttpStatus.OK)
    }

    @GetMapping("/all")
    fun getAllGrade(): ResponseEntity<List<GradeDto>> {
        return ResponseEntity(gradeService.getAllGrade(), HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateGrade(
        @PathVariable("id") gradeId: Long,
        @RequestBody gradeDto: GradeDto
    ): ResponseEntity<GradeDto> {
        return ResponseEntity(gradeService.updateGrade(gradeId, gradeDto), HttpStatus.OK)

    } // delete
}