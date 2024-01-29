package com.side.tiggle.domain.asset.dto.controller

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class AssetUpdateReqDto @JsonCreator constructor (
    @JsonProperty("name") val name: String,
    @JsonProperty("defaults") val defaults: Boolean
)