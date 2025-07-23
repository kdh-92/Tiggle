package com.side.tiggle.global.auth.oauth2

import com.side.tiggle.global.exception.AuthException
import com.side.tiggle.global.exception.error.GlobalErrorCode

class OAuth2Attribute(
    val attributes: Map<String, Any>,
    val attributeKey: String,
    val email: String,
    val profileUrl: String,
    val nickname: String,
    val providerId: String,
    val birthString: String? = null
    ) {

    fun toMap(): Map<String, Any> {
        return mapOf(
            "id" to attributeKey,
            "key" to attributeKey,
            "email" to email,
            "profile_url" to profileUrl,
            "nickname" to nickname
        )
    }

    companion object {
        fun of(
            provider: String,
            attributeKey: String,
            attributes: Map<String, Any>
        ): OAuth2Attribute {
            return when(provider) {
                "google" -> ofGoogle(attributeKey, attributes)
                "naver" -> ofNaver(attributeKey, attributes)
                "kakao" -> ofKakao(attributeKey, attributes)
                else -> throw AuthException(GlobalErrorCode.OAUTH2_AUTHENTICATION_FAILED)
            }
        }

        fun ofGoogle(
            attributeKey: String,
            attributes: Map<String, Any>
        ): OAuth2Attribute {
            return OAuth2Attribute(
                email = attributes["email"] as String,
                profileUrl = attributes["picture"] as String,
                nickname = attributes["name"] as String,
                providerId = attributes["sub"] as String,
                attributes = attributes,
                attributeKey = attributeKey
            )
        }

        fun ofNaver(
            userNameAttributeName: String,
            attributes: Map<String, Any>
        ): OAuth2Attribute {
            val response = attributes["response"] as Map<*, *>
            return OAuth2Attribute(
                email = response["email"] as String,
                nickname = response["name"] as String,
                profileUrl = response["profile_image"] as String,
                providerId = response["id"] as String,
                attributes = attributes,
                attributeKey = userNameAttributeName
            )
        }

        fun ofKakao(
            userNameAttributeName: String,
            attributes: Map<String, Any>
        ): OAuth2Attribute {
            val kakaoAccount = attributes["kakao_account"] as? Map<*, *>
                ?: throw AuthException(GlobalErrorCode.OAUTH2_AUTHENTICATION_FAILED)
            val profile = kakaoAccount["profile"] as? Map<*, *>
                ?: throw AuthException(GlobalErrorCode.OAUTH2_AUTHENTICATION_FAILED)
            return OAuth2Attribute(
                email = kakaoAccount["email"] as? String ?: "",
                nickname = profile["nickname"] as? String ?: "Unknown User",
                profileUrl = profile["profile_image_url"] as? String ?: "",
                providerId = attributes["id"].toString(),
                attributes = attributes,
                attributeKey = userNameAttributeName
            )
        }
    }
}
