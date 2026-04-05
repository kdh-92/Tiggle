package com.side.tiggle.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers

@Component
class MockMvcSupport(
    private val mockMvc: MockMvc
) {

    private val objectMapper = ObjectMapper()
        .registerKotlinModule()
        .registerModule(JavaTimeModule())

    fun getAndReturn(url: String, headers: Map<String, Any>? = null, params: Map<String, String>? = null): MockHttpServletResponse {
        return mockMvc.perform(
            MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .apply {
                    headers?.forEach { (key, value) -> this.header(key, value) }
                }
                .apply {
                    params?.forEach { (key, value) -> this.param(key, value) }
                }
        )
            .andDo(MockMvcResultHandlers.print())
            .andReturn().response
    }

    fun postAndReturn(url: String, request: Any?, headers: Map<String, Any>? = null, params: Map<String, String>? = null): MockHttpServletResponse {
        return mockMvc.perform(
            MockMvcRequestBuilders.post(url)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .apply {
                    headers?.forEach { (key, value) -> this.header(key, value) }
                }
                .apply {
                    params?.forEach { (key, value) -> this.param(key, value) }
                }
        )
            .andDo(MockMvcResultHandlers.print())
            .andReturn().response
    }

    fun putAndReturn(url: String, request: Any?, headers: Map<String, Any>? = null, params: Map<String, String>? = null): MockHttpServletResponse {
        return mockMvc.perform(
            MockMvcRequestBuilders.put(url)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .apply {
                    headers?.forEach { (key, value) -> this.header(key, value) }
                }
                .apply {
                    params?.forEach { (key, value) -> this.param(key, value) }
                }
        )
            .andDo(MockMvcResultHandlers.print())
            .andReturn().response
    }
}