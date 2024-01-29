package com.side.tiggle.domain.tag.model

import javax.persistence.*

@Entity
@Table(name = "tags")
class Tag(
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "defaults", nullable = false)
    val defaults: Boolean = false
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}