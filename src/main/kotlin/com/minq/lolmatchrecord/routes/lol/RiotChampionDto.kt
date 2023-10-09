package com.minq.lolmatchrecord.routes.lol

import com.fasterxml.jackson.annotation.JsonProperty
import com.teamsnowball.model.routes.lol.Image

data class RiotChampionDto(
    val type: String? = "",
    val format: String? = "",
    val version: String? = "",
    val data: Map<String, ChampionInfo>? = emptyMap()
)
data class ChampionInfo(
    @param:JsonProperty("id")
    val id: String? = "",

    @param:JsonProperty("key")
    val key: String? = "",

    @param:JsonProperty("name")
    val name: String? = "",

    @param:JsonProperty("image")
    val image: Image? = null,
)