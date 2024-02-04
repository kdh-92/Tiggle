package com.side.tiggle.domain.asset.model

import javax.persistence.*

@Entity
@Table(name = "assets")
class Asset(
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "defaults", nullable = false)
    val defaults: Boolean
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}