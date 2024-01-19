package com.side.tiggle.domain.txtag.model

import javax.persistence.*

@Entity
@Table(name = "tx_tags")
class TxTag(
    @Column(name = "tx_id", nullable = false)
    val txId: Long,

    @Column(name = "member_id", nullable = false)
    val memberId: Long,

    @Column(name = "tag_names", nullable = false)
    var tagNames: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}