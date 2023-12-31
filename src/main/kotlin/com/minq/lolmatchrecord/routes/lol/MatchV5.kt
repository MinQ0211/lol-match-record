package com.minq.lolmatchrecord.routes.lol

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

data class MatchV5(
    val matchDto: MatchDto? = null,
    val matchTimelineDto: MatchTimelineDto? = null,
)

data class SummonerDTO(
    val accountId: String,	//Encrypted account ID. Max length 56 characters
    val profileIconId: Int,	//ID of the summoner icon associated with the summoner
    val revisionDate: Long,	//Date summoner was last modified specified as epoch milliseconds. The following events will update this timestamp: summoner name change, summoner level change, or profile icon change
    val name: String,	//Summoner name
    val id: String,	//Encrypted summoner ID. Max length 63 characters
    val puuid: String,	//Encrypted PUUID. Exact length of 78 characters
    val summonerLevel: Long,    //Summoner level associated with the summoner
)

data class MatchDto(
    val metadata: MetadataDto? = null,
    val info: InfoDto? = null
)

data class MetadataDto(
    val dataVersion: String? = null,
    val matchId: String? = null,
    val participants: List<String>? = null
)

data class InfoDto(
    val gameCreation: Long? = null,
    val gameDuration: Long? = null,
    val gameEndTimestamp: Long? = null,
    val gameId: Long? = null,
    val gameMode: String? = null,
    val gameName: String? = null,
    val gameStartTimestamp: Long? = null,
    val gameType: String? = null,
    val gameVersion: String? = null,
    val mapId: Int? = null,
    val participants: List<ParticipantDto>? = null,
    val platformId: String? = null,
    val queueId: Int? = null,
    val teams: List<MatchTeamDto>? = null,
    val tournamentCode: String? = null
)

data class ParticipantDto(
    val assists: Int ?= null,
    val baronKills: Int ?= null,
    val bountyLevel: Int ?= null,
    val champExperience: Int ?= null,
    val champLevel: Int ?= null,
    val championId: Int ?= null,
    val championName: String ?= null,
    val championTransform: Int ?= null,
    val consumablesPurchased: Int ?= null,
    val damageDealtToBuildings: Int ?= null,
    val damageDealtToObjectives: Int ?= null,
    val damageDealtToTurrets: Int ?= null,
    val damageSelfMitigated: Int ?= null,
    val deaths: Int ?= null,
    val detectorWardsPlaced: Int ?= null,
    val doubleKills: Int ?= null,
    val dragonKills: Int ?= null,
    val firstBloodAssist: Boolean ?= null,
    val firstBloodKill: Boolean ?= null,
    val firstTowerAssist: Boolean ?= null,
    val firstTowerKill: Boolean ?= null,
    val gameEndedInEarlySurrender: Boolean ?= null,
    val gameEndedInSurrender: Boolean ?= null,
    val goldEarned: Int ?= null,
    val goldSpent: Int ?= null,
    val individualPosition: String ?= null,
    val inhibitorKills: Int ?= null,
    val inhibitorTakedowns: Int? = null,
    val inhibitorsLost: Int ?= null,
    val item0: Int ?= null,
    val item1: Int ?= null,
    val item2: Int ?= null,
    val item3: Int ?= null,
    val item4: Int ?= null,
    val item5: Int ?= null,
    val item6: Int ?= null,
    val itemsPurchased: Int ?= null,
    val killingSprees: Int ?= null,
    val kills: Int ?= null,
    val lane: String ?= null,
    val largestCriticalStrike: Int ?= null,
    val largestKillingSpree: Int ?= null,
    val largestMultiKill: Int ?= null,
    val longestTimeSpentLiving: Int ?= null,
    val magicDamageDealt: Int ?= null,
    val magicDamageDealtToChampions: Int ?= null,
    val magicDamageTaken: Int ?= null,
    val neutralMinionsKilled: Int ?= null,
    val nexusKills: Int ?= null,
    val nexusTakedowns: Int? = null,
    val nexusLost: Int ?= null,
    val objectivesStolen: Int ?= null,
    val objectivesStolenAssists: Int ?= null,
    val participantId: Int ?= null,
    val pentaKills: Int ?= null,
    val perks: PerksDto?= null,
    val physicalDamageDealt: Int ?= null,
    val physicalDamageDealtToChampions: Int ?= null,
    val physicalDamageTaken: Int ?= null,
    val profileIcon: Int ?= null,
    val puuid: String ?= null,
    val quadraKills: Int ?= null,
    val riotIdName: String ?= null,
    val riotIdTagline: String ?= null,
    val role: String ?= null,
    val sightWardsBoughtInGame: Int ?= null,
    val spell1Casts: Int ?= null,
    val spell2Casts: Int ?= null,
    val spell3Casts: Int ?= null,
    val spell4Casts: Int ?= null,
    val summoner1Casts: Int ?= null,
    val summoner1Id: Int ?= null,
    val summoner2Casts: Int ?= null,
    val summoner2Id: Int ?= null,
    val summonerId: String ?= null,
    val summonerLevel: Int ?= null,
    val summonerName: String ?= null,
    val teamEarlySurrendered: Boolean ?= null,
    val teamId: Int ?= null,
    val teamPosition: String ?= null,
    val timeCCingOthers: Int ?= null,
    val timePlayed: Int ?= null,
    val totalDamageDealt: Int ?= null,
    val totalDamageDealtToChampions: Int ?= null,
    val totalDamageShieldedOnTeammates: Int ?= null,
    val totalDamageTaken: Int ?= null,
    val totalHeal: Int ?= null,
    val totalHealsOnTeammates: Int ?= null,
    val totalMinionsKilled: Int ?= null,
    val totalTimeCCDealt: Int ?= null,
    val totalTimeSpentDead: Int ?= null,
    val totalUnitsHealed: Int ?= null,
    val tripleKills: Int ?= null,
    val trueDamageDealt: Int ?= null,
    val trueDamageDealtToChampions: Int ?= null,
    val trueDamageTaken: Int ?= null,
    val turretKills: Int ?= null,
    val turretTakedowns: Int? = null,
    val turretsLost: Int ?= null,
    val unrealKills: Int ?= null,
    val visionScore: Int ?= null,
    val visionWardsBoughtInGame: Int ?= null,
    val wardsKilled: Int ?= null,
    val wardsPlaced: Int ?= null,
    val win: Boolean ?= null,
    val challenges: ChallengesDto?= null,
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ChallengesDto(
    @JsonProperty("12AssistStreakCount")
    val twelveAssistStreakCount: Int ?= null,
    val abilityUses: Int ?= null,
    val acesBefore15Minutes: Int ?= null,
    val alliedJungleMonsterKills: Int ?= null,
    val baronTakedowns: Int ?= null,
    val blastConeOppositeOpponentCount: Int ?= null,
    val bountyGold: Int ?= null,
    val buffsStolen: Int ?= null,
    val completeSupportQuestInTime: Int ?= null,
    val controlWardsPlaced: Int ?= null,
    val damagePerMinute: Double ?= null,
    val damageTakenOnTeamPercentage: Double ?= null,
    val dancedWithRiftHerald: Int ?= null,
    val deathsByEnemyChamps: Int ?= null,
    val dodgeSkillShotsSmallWindow: Int ?= null,
    val doubleAces: Int ?= null,
    val dragonTakedowns: Int ?= null,
    val earlyLaningPhaseGoldExpAdvantage: Int ?= null,
    val effectiveHealAndShielding: Int ?= null,
    val elderDragonKillsWithOpposingSoul: Int ?= null,
    val elderDragonMultikills: Int ?= null,
    val enemyChampionImmobilizations: Int ?= null,
    val enemyJungleMonsterKills: Int ?= null,
    val epicMonsterKillsNearEnemyJungler: Int ?= null,
    val epicMonsterKillsWithin30SecondsOfSpawn: Int ?= null,
    val epicMonsterSteals: Int ?= null,
    val epicMonsterStolenWithoutSmite: Int ?= null,
    val firstTurretKilled: Int ?= null,
    val flawlessAces: Int ?= null,
    val fullTeamTakedown: Int ?= null,
    val gameLength: Double ?= null,
    val getTakedownsInAllLanesEarlyJungleAsLaner: Int ?= null,
    val goldPerMinute: Double ?= null,
    val hadOpenNexus: Int ?= null,
    val immobilizeAndKillWithAlly: Int ?= null,
    val initialBuffCount: Int ?= null,
    val initialCrabCount: Int ?= null,
    val jungleCsBefore10Minutes: Int ?= null,
    val junglerTakedownsNearDamagedEpicMonster: Int ?= null,
    val kTurretsDestroyedBeforePlatesFall: Int ?= null,
    val kda: Double ?= null,
    val killAfterHiddenWithAlly: Int ?= null,
    val killParticipation: Double ?= null,
    val killedChampTookFullTeamDamageSurvived: Int ?= null,
    val killingSprees: Int ?= null,
    val killsNearEnemyTurret: Int ?= null,
    val killsOnOtherLanesEarlyJungleAsLaner: Int ?= null,
    val killsOnRecentlyHealedByAramPack: Int ?= null,
    val killsUnderOwnTurret: Int ?= null,
    val killsWithHelpFromEpicMonster: Int ?= null,
    val knockEnemyIntoTeamAndKill: Int ?= null,
    val landSkillShotsEarlyGame: Int ?= null,
    val laneMinionsFirst10Minutes: Int ?= null,
    val laningPhaseGoldExpAdvantage: Int ?= null,
    val legendaryCount: Int ?= null,
    val lostAnInhibitor: Int ?= null,
    val maxCsAdvantageOnLaneOpponent: Int ?= null,
    val maxKillDeficit: Int ?= null,
    val maxLevelLeadLaneOpponent: Int ?= null,
    val mejaisFullStackInTime: Int ?= null,
    val moreEnemyJungleThanOpponent: Int ?= null,
    val multiKillOneSpell: Int ?= null,
    val multiTurretRiftHeraldCount: Int ?= null,
    val multikills: Int ?= null,
    val multikillsAfterAggressiveFlash: Int ?= null,
    val mythicItemUsed: Int ?= null,
    val outerTurretExecutesBefore10Minutes: Int ?= null,
    val outnumberedKills: Int ?= null,
    val outnumberedNexusKill: Int ?= null,
    val perfectDragonSoulsTaken: Int ?= null,
    val perfectGame: Int ?= null,
    val pickKillWithAlly: Int ?= null,
    val poroExplosions: Int ?= null,
    val quickCleanse: Int ?= null,
    val quickFirstTurret: Int ?= null,
    val quickSoloKills: Int ?= null,
    val riftHeraldTakedowns: Int ?= null,
    val saveAllyFromDeath: Int ?= null,
    val scuttleCrabKills: Int ?= null,
    val skillshotsDodged: Int ?= null,
    val skillshotsHit: Int ?= null,
    val snowballsHit: Int ?= null,
    val soloBaronKills: Int ?= null,
    val soloKills: Int ?= null,
    val stealthWardsPlaced: Int ?= null,
    val survivedSingleDigitHpCount: Int ?= null,
    val survivedThreeImmobilizesInFight: Int ?= null,
    val takedownOnFirstTurret: Int ?= null,
    val takedowns: Int ?= null,
    val takedownsAfterGainingLevelAdvantage: Int ?= null,
    val takedownsBeforeJungleMinionSpawn: Int ?= null,
    val takedownsFirstXMinutes: Int ?= null,
    val takedownsInAlcove: Int ?= null,
    val takedownsInEnemyFountain: Int ?= null,
    val teamBaronKills: Int ?= null,
    val teamDamagePercentage: Double ?= null,
    val teamElderDragonKills: Int ?= null,
    val teamRiftHeraldKills: Int ?= null,
    val tookLargeDamageSurvived: Int ?= null,
    val turretPlatesTaken: Int ?= null,
    val turretTakedowns: Int ?= null,
    val turretsTakenWithRiftHerald: Int ?= null,
    val twentyMinionsIn3SecondsCount: Int ?= null,
    val twoWardsOneSweeperCount: Int ?= null,
    val unseenRecalls: Int ?= null,
    val visionScoreAdvantageLaneOpponent: Double ?= null,
    val visionScorePerMinute: Double ?= null,
    val wardTakedowns: Int ?= null,
    val wardTakedownsBefore20M: Int ?= null,
    val wardsGuarded: Int
)
data class PerksDto(
    val statPerks: PerkStatsDto?=null,
    val styles: List<PerkStyleDto>?=null
)


data class PerkStatsDto(
    val defense: Int?=null,
    val flex: Int?=null,
    val offense: Int?=null,
)


data class PerkStyleDto(
    val description: String?=null,
    val selections: List<PerkStyleSelectionDto>?=null,
    val style: Int?=null,
)
data class PerkStyleSelectionDto(
    val perk: Int?=null,
    val var1: Int?=null,
    val var2: Int?=null,
    val var3: Int?=null,
)
data class MatchTeamDto(
    val bans: List<BanDto>?=null,
    val objectives: ObjectivesDto?=null,
    val teamId: Int?=null,
    val win: Boolean?=null,
)
data class BanDto(
    val championId: Int?=null,
    val pickTurn: Int?=null,
)
data class ObjectivesDto(
    val baron: ObjectiveDto?=null,
    val champion: ObjectiveDto?=null,
    val dragon: ObjectiveDto?=null,
    val inhibitor: ObjectiveDto?=null,
    val riftHerald: ObjectiveDto?=null,
    val tower: ObjectiveDto?=null,
)
data class ObjectiveDto(
    val first: Boolean?=null,
    val kills: Int?=null,
)
data class MatchTimelineDto(
    val metadata: MetadataDto,
    val info: TimelineInfoDto,
)
data class TimelineInfoDto(
    val frameInterval: Long,
    val frames: List<MatchFrameDto>
)
data class MatchFrameDto(
    val participantFrames: Map<String, MatchParticipantFrameDto>,
    val events: List<MatchEventDto>,
    val timestamp: Long
)
data class MatchParticipantFrameDto(
    val championStats: MatchChampionStatsDto,
    val currentGold: Int,
    val damageStats: MatchDamageStatsDto,
    val goldPerSecond: Int,
    val jungleMinionsKilled: Int,
    val level: Int,
    val minionsKilled: Int,
    val participantId: Int,
    val position: MatchPositionDto,
    val timeEnemySpentControlled: Int,
    val totalGold: Int,
    val xp: Int
)
data class MatchChampionStatsDto(
    val abilityHaste: Int,
    val abilityPower: Int,
    val armor: Int,
    val armorPen: Int,
    val armorPenPercent: Int,
    val attackDamage: Int,
    val attackSpeed: Int,
    val bonusArmorPenPercent: Int,
    val bonusMagicPenPercent: Int,
    val ccReduction: Int,
    val cooldownReduction: Int,
    val health: Int,
    val healthMax: Int,
    val healthRegen: Int,
    val lifesteal: Int,
    val magicPen: Int,
    val magicPenPercent: Int,
    val magicResist: Int,
    val movementSpeed: Int,
    val omnivamp: Int,
    val physicalVamp: Int,
    val power: Int,
    val powerMax: Int,
    val powerRegen: Int,
    val spellVamp: Int
)
data class MatchDamageStatsDto(
    val magicDamageDone: Int,
    val magicDamageDoneToChampions: Int,
    val magicDamageTaken: Int,
    val physicalDamageDone: Int,
    val physicalDamageDoneToChampions: Int,
    val physicalDamageTaken: Int,
    val totalDamageDone: Int,
    val totalDamageDoneToChampions: Int,
    val totalDamageTaken: Int,
    val trueDamageDone: Int,
    val trueDamageDoneToChampions: Int,
    val trueDamageTaken: Int
)
data class MatchPositionDto(
    val x: Int,
    val y: Int
)
data class MatchEventDto(
    val type: String,
    val timestamp: Long,
    val laneType: String? = null,
    val skillSlot: Int? = null,
    val ascendedType: String? = null,
    val creatorId: Int? = null,
    val afterId: Int? = null,
    val eventType: String? = null,
    val levelUpType: String? = null,
    val wardType: String? = null,
    val participantId: Int? = null,
    val towerType: String? = null,
    val itemId: Int? = null,
    val beforeId: Int? = null,
    val pointCaptured: String? = null,
    val monsterType: String? = null,
    val monsterSubType: String? = null,
    val teamId: Int? = null,
    val assistingParticipantIds: List<Int>? = null,
    val buildingType: String? = null,
    val victimId: Int? = null,
    val killerId: Int? = null,
    val killStreakLength: Int? = null,
    val position: MatchPositionDto? = null,
    val victimDamageDealt: List<MatchEventVictimDamageDto>? = null,
    val victimDamageReceived: List<MatchEventVictimDamageDto>? = null
)
data class MatchEventVictimDamageDto(
    val basic: Boolean,
    val magicDamage: Int,
    val name: String,
    val participantId: Int,
    val physicalDamage: Int,
    val spellName: String,
    val spellSlot: Int,
    val trueDamage: Int,
    val type: String
)