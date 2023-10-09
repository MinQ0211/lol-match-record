package com.minq.lolmatchrecord.interfacestructure

import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientResponseException
import org.springframework.web.client.RestTemplate
import java.net.URI

@Component
class Http() {
    private val log = LoggerFactory.getLogger(this::class.java)
    private val restTemplate: RestTemplate = RestTemplateBuilder().messageConverters().build()

    @Retryable(maxAttempts = 3, backoff = Backoff(delay = 1000))
    fun <T> get(url: String, headers: Map<String, String>, responseClass: Class<T>): T? {
        val httpHeaders = HttpHeaders()
        httpHeaders.setAll(headers)
        return restTemplate.exchange(
            URI.create(url),
            HttpMethod.GET,
            HttpEntity<Any>(httpHeaders),
            responseClass
        ).body
    }
    fun get(url: String): String? {
        return get(url, emptyMap(), String::class.java)
    }
    fun <T> post(
        url: String,
        headers: Map<String, String>,
        username: String?,
        password: String?,
        body: Any?,
        responseClass: Class<T>
    ): T? {
        val httpHeaders = HttpHeaders()
        httpHeaders.setAll(headers)
        if (username?.isNotBlank() == true && password?.isNotBlank() == true) {
            httpHeaders.setBasicAuth(username, password)
        }
        return try {
            restTemplate.exchange(
                URI.create(url),
                HttpMethod.POST,
                HttpEntity(body, httpHeaders),
                responseClass
            ).body
        } catch (e: RestClientResponseException) {
            null
        }
    }
}
