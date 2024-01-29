package com.side.tiggle.global.exception

import com.side.tiggle.global.exception.CustomException

class NotAuthenticatedException(
        override val message: String? = null
): CustomException(

) {
}