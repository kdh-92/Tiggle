package com.side.tiggle.global.common.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

val Any.log: Logger
    get() = LoggerFactory.getLogger(this::class.java.name)
