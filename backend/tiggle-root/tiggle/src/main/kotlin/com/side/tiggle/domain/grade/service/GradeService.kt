package com.side.tiggle.domain.grade.service

import com.side.tiggle.domain.grade.GradeDto
import com.side.tiggle.domain.grade.model.Grade
import com.side.tiggle.domain.grade.repository.GradeRepository
import com.side.tiggle.global.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class GradeService(
    private val gradeRepository: GradeRepository
) {
    fun createGrade(gradeDto: GradeDto): GradeDto {
        val grade = gradeDto.toEntity()
        return GradeDto.fromEntity(gradeRepository.save(grade))
    }

    fun getGrade(gradeId: Long): GradeDto {
        return GradeDto.fromEntity(gradeRepository.findById(gradeId)
            .orElseThrow { NotFoundException() })
    }

    fun getAllGrade(): List<GradeDto> {
        return gradeRepository.findAll().map { GradeDto.fromEntity(it) }
    }

    fun updateGrade(gradeId: Long, gradeDto: GradeDto): GradeDto {
        val grade = gradeRepository.findById(gradeId).orElseThrow { NotFoundException() }

        grade.name = gradeDto.name
        grade.imageUrl = gradeDto.imageUrl
        grade.description = gradeDto.description

        return GradeDto.fromEntity(gradeRepository.save(grade))
    }

    // delete
}