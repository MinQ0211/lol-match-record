package com.minq.lolmatchrecord.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.NotFound::class)
    fun handleHttpClientNotFoundException(ex: HttpClientErrorException.NotFound): ResponseEntity<Any> {
        val status = ex.statusCode
        val errorResponse = mapOf("message" to "Summonername Not found", "status" to status.value())
        return ResponseEntity(errorResponse, status)
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden::class)
    fun handleHttpClientForbiddenException(ex: HttpClientErrorException, request: WebRequest): ResponseEntity<Any> {
        val status = ex.statusCode
        val errorResponse = mapOf("message" to "API-KEY Not valid", "status" to status.value())

        return ResponseEntity(errorResponse, status)
    }

    @ExceptionHandler(HttpClientErrorException::class)
    fun handleHttpClientErrorException(ex: HttpClientErrorException, request: WebRequest): ResponseEntity<Any> {
        ex.printStackTrace()
        val status = ex.statusCode
        val errorResponse = mapOf("message" to "External API error", "status" to status.value())

        return ResponseEntity(errorResponse, status)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<Any> {
        val errorResponse = mapOf("message" to ex.message, "status" to HttpStatus.BAD_REQUEST.value())
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<Any> {
        ex.printStackTrace()

        val errorResponse = mapOf("message" to "Internal server error", "status" to HttpStatus.INTERNAL_SERVER_ERROR.value())
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<Any> {
        val errorResponse = mapOf("message" to "Unexpected error", "status" to HttpStatus.INTERNAL_SERVER_ERROR.value())
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}