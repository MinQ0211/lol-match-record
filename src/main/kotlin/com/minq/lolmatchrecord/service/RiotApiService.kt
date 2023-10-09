package com.minq.lolmatchrecord.service

import com.minq.lolmatchrecord.model.dto.response.QueueEnum
import com.minq.lolmatchrecord.interfacestructure.Http
import com.minq.lolmatchrecord.model.converter.LolMatchConverter
import com.minq.lolmatchrecord.model.dto.response.LolMatchDto
import com.minq.lolmatchrecord.routes.lol.MatchDto
import com.minq.lolmatchrecord.routes.lol.MatchTimelineDto
import com.minq.lolmatchrecord.routes.lol.MatchV5
import com.minq.lolmatchrecord.routes.lol.SummonerDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder

@Service
class RiotApiService @Autowired constructor(
    private val http: Http,
    ) {
    private var apiHost = "api.riotgames.com"

    fun <T> request(apiKey:String, uri: String, param: Map<String,String>?=null,header: Map<String,String>?=null,responseType: Class<T>, region: String? = "asia"): T? {
        val url = "https://${region}.$apiHost$uri"
        val headers = mapOf<String,String>("X-Riot-Token" to apiKey)
        val data = http.get(
            UriComponentsBuilder.fromHttpUrl(url).apply { param?.mapNotNull { queryParam(it.key, it.value) } }.toUriString(),
            header?:headers,
            responseType
        )
        return data
    }
    fun getSummonersByName(apiKey:String, summonerName: String): SummonerDTO? {
        return request(apiKey, "/lol/summoner/v4/summoners/by-name/${summonerName}", responseType = SummonerDTO::class.java, region="kr")
    }
    fun getMatches(apiKey:String, puuid: String, queue: QueueEnum, param: Map<String, String>?): Array<String>? {
        val deafultParam = mutableMapOf<String,String>(
            "start" to "0",
            "count" to "20"
        )
        queue.id?.let { deafultParam.put("queue", it.toString()) }
        return request(apiKey, "/lol/match/v5/matches/by-puuid/${puuid}/ids", deafultParam + (param ?: emptyMap()),responseType = Array<String>::class.java,)
    }
    fun getMatch(apiKey:String, matchId: String): MatchDto? {
        return request(apiKey, "/lol/match/v5/matches/${matchId}", responseType = MatchDto::class.java)
    }
    fun getMatchTimeLine(apiKey:String, matchId: String): MatchTimelineDto? {
        return request(apiKey, "/match/v5/matches/${matchId}/timeline", responseType = MatchTimelineDto::class.java)
    }
    @Cacheable(value = ["matchRecord.match"], key = "'gameId-'+#gameId")
    fun lolGameSearch(apiKey: String, gameId: String): MatchV5? {
        return MatchV5(
            getMatch(apiKey, gameId),
            null
        )
    }
}