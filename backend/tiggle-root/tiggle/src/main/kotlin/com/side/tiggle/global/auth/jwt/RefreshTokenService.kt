package com.side.tiggle.global.auth.jwt

import com.side.tiggle.domain.member.model.Member

interface RefreshTokenService {
    fun saveRefreshToken(memberId: Long, refreshToken: String)
    fun validateRefreshToken(refreshToken: String): Boolean
    fun deleteRefreshToken(memberId: Long)
    fun findMemberByRefreshToken(refreshToken: String): Member?
}