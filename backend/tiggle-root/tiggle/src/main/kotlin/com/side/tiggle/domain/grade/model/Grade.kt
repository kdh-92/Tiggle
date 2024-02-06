package com.side.tiggle.domain.grade.model

import com.side.tiggle.global.common.model.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "grades")
class Grade(
    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "image_url", nullable = false)
    var imageUrl: String,

    @Column(name = "description", nullable = false)
    var description: String
): BaseEntity() {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}