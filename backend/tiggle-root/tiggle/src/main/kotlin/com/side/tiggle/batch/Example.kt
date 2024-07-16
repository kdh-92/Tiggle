package com.side.tiggle.batch

import javax.persistence.*

@Entity
@Table(name = "example")
class Example(
    private var name: String? = null
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = 0
}