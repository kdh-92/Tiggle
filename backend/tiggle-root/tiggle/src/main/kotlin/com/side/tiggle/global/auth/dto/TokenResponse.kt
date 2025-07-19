package com.side.tiggle.global.auth.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String? = null
)