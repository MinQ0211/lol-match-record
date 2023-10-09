package com.teamsnowball.model.routes.lol

import com.fasterxml.jackson.annotation.JsonProperty

data class RiotItemDto(
    val type: String? = "",
    val version: String? = "",
    val data: Map<String, ItemInfo>? = emptyMap()
)

data class ItemInfo(
    @param:JsonProperty("name")
    val name: String? = "",

    @param:JsonProperty("image")
    val image: Image? = null,
)
