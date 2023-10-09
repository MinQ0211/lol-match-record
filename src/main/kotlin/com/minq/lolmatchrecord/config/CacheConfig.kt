package com.minq.lolmatchrecord.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration


@Configuration
@EnableCaching
class CacheConfig {
    @Value("\${spring.data.redis.host}")
    private val redisHost: String = "localhost"
    @Value("\${spring.data.redis.port}")
    private val redisPort = 6379


    @Bean(name = ["redisConnectionFactory"])
    fun localRedisConnectionFactory(): RedisConnectionFactory {
        val redisConf = RedisStandaloneConfiguration(redisHost, redisPort)
        return LettuceConnectionFactory(redisConf)
    }
    @Bean
    fun cacheManager(): CacheManager {
        val objectMapper = ObjectMapper()
            .registerModule(KotlinModule.Builder().build())
            .registerModule(JavaTimeModule())
            .activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Any::class.java).build(), ObjectMapper.DefaultTyping.EVERYTHING)

        val defaultCacheConfiguration: RedisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(GenericJackson2JsonRedisSerializer(objectMapper)))
            .entryTtl(Duration.ofDays(14))

        val userCacheConfiguration: RedisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(GenericJackson2JsonRedisSerializer(objectMapper)))

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(localRedisConnectionFactory())
            .cacheDefaults(defaultCacheConfiguration)
            .withInitialCacheConfigurations(
                hashMapOf(
                    Pair("matchRecord.summonerName", userCacheConfiguration.entryTtl(Duration.ofMinutes(15))),
                    Pair("matchRecord.match", userCacheConfiguration.entryTtl(Duration.ofMinutes(15))),
                )
            ).build()
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(localRedisConnectionFactory())
        template.keySerializer = StringRedisSerializer()
        template.hashValueSerializer = GenericToStringSerializer(Any::class.java)
        template.valueSerializer = GenericToStringSerializer(Any::class.java)
        return template
    }
    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config()
        config.useSingleServer().address = "redis://$redisHost:$redisPort"
        return Redisson.create(config)
    }
}

