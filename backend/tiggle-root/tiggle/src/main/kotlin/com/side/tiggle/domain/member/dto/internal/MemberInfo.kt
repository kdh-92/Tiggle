package com.side.tiggle.domain.member.dto.internal

import com.side.tiggle.domain.member.model.Member

data class MemberInfo(
    val id: Long,
    val nickname: String,
    val profileUrl: String?
) {
    companion object {
        fun from(member: Member): MemberInfo {
            return MemberInfo(
                id = member.id,
                nickname = member.nickname,
                profileUrl = member.profileUrl
            )
        }
    }
}