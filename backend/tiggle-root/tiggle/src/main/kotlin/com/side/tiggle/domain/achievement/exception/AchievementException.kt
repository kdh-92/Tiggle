package com.side.tiggle.domain.achievement.exception

import com.side.tiggle.global.exception.CustomException
import com.side.tiggle.global.exception.error.ErrorCode

class AchievementException : CustomException {

    private val errorCode: ErrorCode

    constructor(errorCode: ErrorCode) : super(errorCode) {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode, cause) {
        this.errorCode = errorCode
    }

    override fun getErrorCode(): ErrorCode = errorCode
}
