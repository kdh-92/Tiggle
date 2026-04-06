package com.side.tiggle.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

val jacksonMapper = ObjectMapper()
    .registerModule(JavaTimeModule())
    .registerKotlinModule()

inline fun <reified T> String.toJson(): T = jacksonMapper.readValue(this, object: TypeReference<T>(){})
fun Any.fromJson(): String = jacksonMapper.writeValueAsString(this)