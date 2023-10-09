package com.minq.lolmatchrecord.model.converter

import com.minq.lolmatchrecord.model.dto.response.*
import com.minq.lolmatchrecord.routes.lol.InfoDto
import com.minq.lolmatchrecord.routes.lol.MatchTeamDto
import com.minq.lolmatchrecord.routes.lol.MatchV5
import com.minq.lolmatchrecord.routes.lol.ParticipantDto
import com.minq.lolmatchrecord.service.LolSubApiService
import com.minq.lolmatchrecord.util.TimeUtil
import org.mapstruct.*
import org.mapstruct.ReportingPolicy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import kotlin.math.abs
import kotlin.math.round


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
abstract class LolMatchConverter(
): Converter<MatchV5, LolMatchDto> {
        @Autowired
        protected lateinit var lolSubApiUtil: LolSubApiService
        @Autowired
        private lateinit var lolSummonerConverter : LolSummonerConverter
        @Mappings(
                Mapping(target = "version", source = "matchDto.info.gameVersion"),
                Mapping(target = "gameId", source = "matchDto.info.gameId"),
                Mapping(target = "gameLengthSecond", source = "matchDto.info.gameDuration"),
                Mapping(target = "gameCreation",  qualifiedByName = ["toGameCreation"] , source = "matchDto.info.gameCreation"),
                Mapping(target = "queue",  qualifiedByName = ["toQueue"] , source = "matchDto.info"),
                Mapping(target = "teams",  qualifiedByName = ["toTeams"] , source = "matchDto.info.teams"),
                Mapping(target = "participants",  qualifiedByName = ["toParticipants"] , source = "."),
        )
        abstract fun convert(source: MatchV5?): LolMatchDto

        @Named("toGameCreation")
        fun toGameCreation(source: Long?): String? {
                return source?.let { TimeUtil().epochToDateTime(it) }
        }
        @Named("toQueue")
        fun toQueue(source: InfoDto?): LolQueueViewDto? {
                val version = source?.gameVersion?.replace(Regex("(\\d+\\.\\d+)\\..*"), "$1.1")
                val riotMap = version?.let { lolSubApiUtil.getMap(it) }
                return LolQueueViewDto(
                        id = source?.queueId,
                        mapId = source?.mapId,
                        queueName =  riotMap?.data?.values?.find { it.mapId?.toIntOrNull() == source?.mapId }?.mapName,
                        gameType =  source?.gameType
                )
        }
        @Named("toTeams")
        fun toQueue(source: List<MatchTeamDto>?): List<LolTeamViewDto>? {
                return source?.mapNotNull {
                        LolTeamViewDto(
                                side = if(it.teamId==100) "blue" else "red",
                                win = it.win
                        )
                }
        }
        @Named("toParticipants")
        fun toParticipants(source: MatchV5?): List<LolParticipantViewDto>? {
                val version = source?.matchDto?.info?.gameVersion?.replace(Regex("(\\d+\\.\\d+)\\..*"), "$1.1")
                val participants = source?.matchDto?.info?.participants
                val champion = version?.let { lolSubApiUtil.getChampion(it) }?.data
                return participants?.mapNotNull {participant ->
                        LolParticipantViewDto(
                                position = participant.individualPosition,
                                summoner = lolSummonerConverter.convert(participant),
                                champion = LolChampionViewDto(
                                        championId = participant.championId,
                                        name = champion?.filterValues { it.key==participant.championId.toString() }?.values?.first()?.name?:"",
                                        thumbnail = champion?.filterValues { it.key==participant.championId.toString() }?.values?.first()?.image?.full?.let { "https://ddragon.leagueoflegends.com/cdn/${version}/img/champion/$it" }?:"",
                                        splash=  champion?.filterValues { it.key==participant.championId.toString() }?.values?.first()?.id?.let { "http://ddragon.leagueoflegends.com/cdn/img/champion/loading/${it}_0.jpg" }?:"",
                                ),
                                team = LolTeamViewDto(
                                        side = if(participant.teamId==100) "blue" else "red",
                                        win = participant.win
                                ),
                                item = listOf<LolItemViewDto?>(
                                        participant?.item0?.let { lolItem(version, it) },
                                        participant?.item1?.let { lolItem(version, it) },
                                        participant?.item2?.let { lolItem(version, it) },
                                        participant?.item3?.let { lolItem(version, it) },
                                        participant?.item4?.let { lolItem(version, it) },
                                        participant?.item5?.let { lolItem(version, it) },
                                ),
                                trinket = participant?.item6?.let { lolItem(version, it) },
                                runes = listOf(
                                        lolRune(version,participant.perks?.styles?.filter { it.description=="primaryStyle" }?.firstOrNull()?.style,1),
                                        lolRune(version,participant.perks?.styles?.filter { it.description=="subStyle" }?.firstOrNull()?.style,2),
                                ),
                                spells = listOf(
                                        lolSpell(version,participant.summoner1Id,1),
                                        lolSpell(version,participant.summoner2Id,2),
                                ),
                                stat = LolStatViewDto(
                                        level=participant?.champLevel,
                                        kill = participant?.kills,
                                        death = participant?.deaths,
                                        assist = participant?.assists,
                                        kda = if (participant.deaths==null || participant.deaths==0){
                                                (participant.kills?:0) + (participant.assists?:0)  * 1.2
                                        }else{
                                                safeDiv((participant.kills?:0) + (participant.assists?:0),participant.deaths) }
                                        ,
                                        cs = participant?.totalMinionsKilled,
                                        snowballsHit = participant?.challenges?.snowballsHit,
                                        visionScore = participant?.visionScore,
                                        goldEarned = participant?.goldEarned,
                                        totalDamageDealtToChampions = participant?.totalDamageDealtToChampions,
                                )
                        )
                }
        }
        protected fun <T : Number, R : Number> safeDiv(a: T?, b: R?): Double {
                val safeDivider = if (abs((b ?: 0).toDouble()) > 0.0) b else 1
                val result = (a ?: 0).toDouble() / safeDivider!!.toDouble()
                return round(result * 100) / 100
        }
        fun lolItem(version:String?,itemId: Int): LolItemViewDto {
                val item = version?.let { lolSubApiUtil.getItem(it)?.data?.get(itemId.toString()) }
                return LolItemViewDto(
                        itemId = itemId,
                        name = item?.name?:"",
                        thumbnail = item?.image?.full?.let{"https://ddragon.leagueoflegends.com/cdn/13.12.1/img/item/${it}"}?:""
                )
        }
        fun lolRune(version:String?,runeId: Int?, order: Int): LolSubViewDto {
                val rune = version?.let { lolSubApiUtil.getRune(it) }
                val icon = rune?.find { it.id == runeId }?.icon ?:
                rune?.flatMap { it.slots.flatMap { slot -> slot.runes } }
                        ?.find { it.id == runeId }?.icon

                return LolSubViewDto(
                        id = runeId?:0,
                        image = icon?.let { "https://ddragon.leagueoflegends.com/cdn/img/$it" }?:"",
                        order = order,
                )
        }
        fun lolSpell(version:String?,spellId: Int?, order: Int): LolSubViewDto {
                val spell = version?.let { lolSubApiUtil.getSpell(it)?.data?.filter { it.value.key==spellId.toString() }?.values?.firstOrNull() }

                return LolSubViewDto(
                        id = spellId?:0,
                        image = spell?.image?.full?.let { "https://ddragon.leagueoflegends.com/cdn/13.12.1/img/spell/${it}" }?:"",
                        order = order,
                )
        }
}
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
abstract class LolSummonerConverter(
): Converter<ParticipantDto, LolSummonerViewDto> {
        @Mappings(
                Mapping(target = "name", source = "summonerName"),
                Mapping(target = "puuid", source = "puuid"),
                )
        abstract fun convert(source: ParticipantDto?): LolSummonerViewDto
}