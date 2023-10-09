package com.teamsnowball.model.routes.lol

import com.fasterxml.jackson.annotation.JsonProperty

data class RiotMapDto(
    val type: String? = "",
    val version: String? = "",
    val data: Map<String, MapInfo>? = emptyMap()
)

data class MapInfo(
    @param:JsonProperty("mapName")
    val mapName: String? = "",

    @param:JsonProperty("mapId")
    val mapId: String? = "",

    @param:JsonProperty("image")
    val image: Image? = null,
)

data class Image(
    val full: String? = "",
    val sprite: String? = "",
    val group: String? = "",
    val x: Int? = 0,
    val y: Int? = 0,
    val w: Int? = 0,
    val h: Int? = 0
)