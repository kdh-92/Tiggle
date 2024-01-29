package com.side.tiggle.domain.grade

import com.side.tiggle.domain.grade.model.Grade

data class GradeDto(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val description: String
) {

    fun toEntity(): Grade {
        val grade = Grade (
            name = this.name,
            imageUrl = this.imageUrl,
            description = this.description
        )
        grade.id = this.id
        return grade
    }
    companion object {
        fun fromEntity(grade: Grade): GradeDto{
            return GradeDto(
                id = grade.id,
                name = grade.name,
                imageUrl = grade.imageUrl,
                description = grade.description
            )
        }
    }
}