package com.minq.lolmatchrecord.controller

import com.minq.lolmatchrecord.model.dto.response.LolMatchDto
import com.minq.lolmatchrecord.model.dto.response.QueueEnum
import com.minq.lolmatchrecord.model.dto.response.SummonerViewDto
import com.minq.lolmatchrecord.service.ApiService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.servlet.http.HttpServletRequest
import org.springdoc.core.service.OpenAPIService
import org.springframework.cache.annotation.CacheConfig
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
@CacheConfig
class ApiController(
    private val apiService: ApiService
){
    @GetMapping("/lol/by-summoner/{summonerName}")
    @Operation(security = [SecurityRequirement(name = "RIOT API KEY")])
    fun getUserLol(
        request: HttpServletRequest,
        @PathVariable summonerName: String,
    ): ResponseEntity<SummonerViewDto> {
        val apiKey = request.getHeader("api-key")?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val body = apiService.getUserLol(apiKey,summonerName)
        return ResponseEntity(body, HttpStatus.OK)
    }

    @GetMapping("/match/lol/by-summoner")
    fun getMatchLolSummonerName(
        request: HttpServletRequest,
        @RequestParam(required = false) summonerName: String,
        @RequestParam(required = false) queue: QueueEnum,
        @RequestParam(required = false) datetime: String?,
    ): ResponseEntity<List<LolMatchDto?>> {
        val apiKey = request.getHeader("api-key")?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val puuid = apiService.getUserLol(apiKey,summonerName)?.puuid
        val body = puuid?.let {
            apiService.getMatchLolPuuid(apiKey, it, queue, datetime)
        }
        return ResponseEntity(body, HttpStatus.OK)
    }
}