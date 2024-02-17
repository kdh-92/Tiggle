package com.side.tiggle.domain.category.model

import javax.persistence.*

@Entity
@Table(name = "categories")
class Category(
    var name: String,
    @Enumerated(EnumType.STRING)
    var type: CategoryType,
    var defaults: Boolean
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}