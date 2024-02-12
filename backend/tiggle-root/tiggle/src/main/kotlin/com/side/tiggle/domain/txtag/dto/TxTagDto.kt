package com.side.tiggle.domain.txtag.dto

import com.side.tiggle.domain.txtag.model.TxTag

data class TxTagDto (
    val id: Long,
    val txId: Long,
    val memberId: Long,
    val tagNames: String?
) {

    fun toEntity(dto: TxTagDto): TxTag {
        return TxTag(
            txId = dto.txId,
            memberId = dto.memberId,
            tagNames = dto.tagNames
        )
    }

    companion object {
        fun fromEntity(txTag: TxTag): TxTagDto {
            return TxTagDto(
                id = txTag.id,
                txId = txTag.txId,
                memberId = txTag.memberId,
                tagNames = txTag.tagNames
            )
        }
    }
}