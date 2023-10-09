package com.minq.lolmatchrecord.model.dto.response

data class LolMatchDto (
    var selected: Boolean?=false,
    var gameId: String?=null,
    var version: String?=null,
    var gameLengthSecond: Int?=null,
    var gameCreation: String?=null,
    var queue: LolQueueViewDto?=null,
    var teams: List<LolTeamViewDto>? = null,
    var participants: List<LolParticipantViewDto>? = null,
)
data class SummonerViewDto(
    val profileIconImage: String?,	//ID of the summoner icon associated with the summoner
    val name: String,	//Summoner name
    val puuid: String,	//Encrypted PUUID. Exact length of 78 characters
    val summonerLevel: Long,    //Summoner level associated with the summoner
)
data class LolQueueViewDto(
    val id: Int?,
    val mapId: Int?,
    val queueName: String?,
    val gameType: String?,
)
enum class QueueEnum(val id: Int?) {
    ALL(null),
    ARAM(450),
    SOLORANK(420),
    FLEXRANK(440),
    BLINKPICK5V5(430)
}
data class LolTeamViewDto(
    val side: String,
    val win: Boolean?=null,
)
data class  LolParticipantViewDto(
    var requestUser: Boolean? = false,
    val position: String?=null,
    val summoner: LolSummonerViewDto? = null,
    val champion: LolChampionViewDto? = null,
    val team: LolTeamViewDto? = null,
    val item : List<LolItemViewDto?>? = null,
    val trinket: LolItemViewDto? = null,
    val runes: List<LolSubViewDto?>? = null,
    val spells: List<LolSubViewDto?>? = null,
    val stat: LolStatViewDto? = null,
)
data class  LolStatViewDto(
    val kill: Int? = null,
    val death: Int? = null,
    val assist: Int? = null,
    val kda: Double? = null,
    val cs: Int? = null,
    val snowballsHit: Int? = null,
    val visionScore: Int ?= null,
    val goldEarned: Int ?= null,
    val level: Int ?= null,
    val totalDamageDealtToChampions: Int ?= null,
)
data class LolChampionViewDto(
    val championId: Int?=null,
    val name: String,
    val thumbnail: String,
    val splash: String,
)
data class LolItemViewDto(
    val itemId: Int,
    val name: String,
    val thumbnail: String,
)
data class LolSubViewDto(
    val id: Int,
    val image: String?,
    val order: Int,
)
data class LolSummonerViewDto(
    val name: String,
    val puuid: String,
)
