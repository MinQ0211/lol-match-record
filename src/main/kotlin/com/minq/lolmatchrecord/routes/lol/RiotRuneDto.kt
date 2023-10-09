package com.teamsnowball.model.routes.lol

import com.fasterxml.jackson.annotation.JsonProperty

data class RiotRuneDto(
    @JsonProperty("id")
    val id: Int,

    @JsonProperty("key")
    val key: String,

    @JsonProperty("icon")
    val icon: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("slots")
    val slots: List<Slot>
)

data class Slot(
    @JsonProperty("runes")
    val runes: List<Rune>
)

data class Rune(
    @JsonProperty("id")
    val id: Int,

    @JsonProperty("key")
    val key: String,

    @JsonProperty("icon")
    val icon: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("shortDesc")
    val shortDesc: String,

    @JsonProperty("longDesc")
    val longDesc: String
)