package com.minq.lolmatchrecord.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment


@Configuration

class SwaggerConfig(
    private val environment: Environment
) {
    var securityScheme = SecurityScheme()
        .type(SecurityScheme.Type.APIKEY)
        .`in`(SecurityScheme.In.HEADER)
        .name("api-key")
    var schemaRequirement: SecurityRequirement = SecurityRequirement().addList("RIOT API KEY")

    @Bean
    fun openAPI(
        @Value("\${springdoc.version}") springdocVersion: String?,
        @Value("\${springdoc.title}") springdocTitle: String?,
        @Value("\${springdoc.description}") springdocDescription: String?
    ): OpenAPI? {
        val info: Info = Info()
            .title(springdocTitle)
            .version(springdocVersion)
            .description(springdocDescription)
        return OpenAPI()
            .components(
                Components().addSecuritySchemes(
                "RIOT API KEY",
                securityScheme
            ))
            .security(listOf(schemaRequirement))
            .info(info)
    }
}