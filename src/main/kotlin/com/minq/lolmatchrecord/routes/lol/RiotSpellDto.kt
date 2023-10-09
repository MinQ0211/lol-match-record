package com.teamsnowball.model.routes.lol

import com.fasterxml.jackson.annotation.JsonProperty

data class RiotSpellDto(
    val type: String? = "",
    val version: String? = "",
    val data: Map<String, SpellInfoDto>? = emptyMap()
)

data class SpellInfoDto(
    @param:JsonProperty("name")
    val name: String? = "",

    @param:JsonProperty("id")
    val id: String? = "",

    @param:JsonProperty("key")
    val key: String? = "",

    @param:JsonProperty("image")
    val image: Image? = null,
)
