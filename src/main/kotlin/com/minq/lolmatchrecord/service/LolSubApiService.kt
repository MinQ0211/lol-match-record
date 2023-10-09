package com.minq.lolmatchrecord.service


import com.minq.lolmatchrecord.interfacestructure.Http
import com.minq.lolmatchrecord.routes.lol.RiotChampionDto
import com.teamsnowball.model.routes.lol.RiotItemDto
import com.teamsnowball.model.routes.lol.RiotMapDto
import com.teamsnowball.model.routes.lol.RiotRuneDto
import com.teamsnowball.model.routes.lol.RiotSpellDto
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class LolSubApiService(
) {
    private val http = Http()
    @Cacheable("lol.getVersion")
    fun getVersion(): Array<String>? {
        val url = "https://ddragon.leagueoflegends.com/api/versions.json"
        return http.get(
            url, emptyMap(), Array<String>::class.java
        )
    }
    @Cacheable("lol.getMap", key = "#region + ':' +  #version")
    fun getMap(version:String, region:String = "ko_KR"): RiotMapDto? {
        val url = "https://ddragon.leagueoflegends.com/cdn/${version}/data/${region}/map.json"
        return http.get(
            url, emptyMap(), RiotMapDto::class.java
        )
    }
    @Cacheable("lol.getChampion", key = "#region + ':' +  #version")
    fun getChampion(version:String, region:String = "ko_KR"): RiotChampionDto? {
        val url = "https://ddragon.leagueoflegends.com/cdn/${version}/data/${region}/champion.json"
        return http.get(
            url, emptyMap(), RiotChampionDto::class.java
        )
    }
    @Cacheable("lol.getItem", key = "#region + ':' +  #version")
    fun getItem(version:String, region:String = "ko_KR"): RiotItemDto? {
        val url = "https://ddragon.leagueoflegends.com/cdn/${version}/data/${region}/item.json"
        return http.get(
            url, emptyMap(), RiotItemDto::class.java
        )
    }
    @Cacheable("lol.getSpell", key = "#region + ':' +  #version")
    fun getSpell(version:String, region:String = "ko_KR"): RiotSpellDto? {
        val url = "https://ddragon.leagueoflegends.com/cdn/${version}/data/${region}/summoner.json"
        return http.get(
            url, emptyMap(), RiotSpellDto::class.java
        )
    }

    @Cacheable("lol.getRune", key = "#region + ':' +  #version")
    fun getRune(version:String, region:String = "ko_KR"): Array<RiotRuneDto>? {
        val url = "https://ddragon.leagueoflegends.com/cdn/${version}/data/${region}/runesReforged.json"
        return http.get(
            url, emptyMap(), Array<RiotRuneDto>::class.java
        )
    }
}