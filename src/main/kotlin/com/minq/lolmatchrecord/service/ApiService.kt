package com.minq.lolmatchrecord.service

import com.minq.lolmatchrecord.model.converter.LolMatchConverter
import com.minq.lolmatchrecord.model.dto.response.LolMatchDto
import com.minq.lolmatchrecord.model.dto.response.QueueEnum
import com.minq.lolmatchrecord.model.dto.response.SummonerViewDto
import com.minq.lolmatchrecord.routes.lol.MatchV5
import com.minq.lolmatchrecord.util.TimeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class ApiService(
    private val riotApiService: RiotApiService,
    private val lolSubApiService: LolSubApiService,
    private val lolMatchConverter: LolMatchConverter,
    ){
    @Cacheable(value = ["matchRecord.summonerName"], key = "'summonerName-'+#summonerName")
    fun getUserLol(apiKey: String, summonerName: String): SummonerViewDto? {
        return summonerName?.let { riotApiService.getSummonersByName(apiKey, it) }?.let {
            SummonerViewDto(
                profileIconImage = it.profileIconId?.let { "https://ddragon.leagueoflegends.com/cdn/${lolSubApiService.getVersion()?.firstOrNull()}/img/profileicon/$it.png" }?:null,
                name = it.name,
                puuid = it.puuid,
                summonerLevel = it.summonerLevel,
            )
        }
    }
    fun getMatchLolPuuid(apiKey: String, puuid: String, queue: QueueEnum, datetime: String?): List<LolMatchDto?>? {
        return lolMatchSearch(apiKey, puuid, queue, datetime)
    }

    fun lolMatchSearch(apiKey: String, puuid: String,queue: QueueEnum, datetime: String?): List<LolMatchDto?> {
        val gameList = mutableListOf<LolMatchDto?>()
        val endTime = mutableMapOf("startTime" to "1672531200")//2023 00:00:00 GMT+0000
        datetime?.let { endTime?.put("endTime" ,(TimeUtil().dateTimeToEpoch(it)/1000).toString())}
        val matches: List<String>? = riotApiService.getMatches(apiKey, puuid, queue, endTime )?.toList()
        runBlocking {
            matches?.mapNotNull {
                async(Dispatchers.IO) {
                    if(it.isNotEmpty()){
                        val games = riotApiService.lolGameSearch(apiKey, it)?.let {
                            lolMatchConverter.convert(it)
                        }.apply {
                            this?.participants?.mapNotNull {
                                if(it.summoner?.puuid==puuid){
                                    it.requestUser=true
                                }
                            }
                        }
                        games?.gameId?.let {
                            gameList.add(
                                games
                            )
                        }
                    }
                }
            }?.awaitAll()
        }
        return gameList.sortedByDescending { it?.gameCreation }.toList()
    }
}
