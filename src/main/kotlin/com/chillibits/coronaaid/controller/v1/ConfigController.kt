package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.exception.exception.ConfigItemNotFoundException
import com.chillibits.coronaaid.model.dto.ConfigItemDto
import com.chillibits.coronaaid.repository.ConfigRepository
import com.chillibits.coronaaid.model.mapper.toDto
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "Config REST Endpoint", tags = ["config"])
class ConfigController {

    @Autowired
    private lateinit var configRepository: ConfigRepository

    @GetMapping(
            path = ["/config"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Returns all config items")
    fun getAllConfigItems(): Set<ConfigItemDto> = configRepository.findAll().map { it.toDto() }.toSet()

    @GetMapping(
            path = ["/config/{configKey}"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiResponses(
            ApiResponse(code = 404, message = "Config item not found")
    )
    @ApiOperation("Returns a config item by its name")
    fun getSingleConfigItem(@PathVariable configKey: String): ConfigItemDto? {
        val configValue = configRepository.findByConfigKey(configKey) ?: throw ConfigItemNotFoundException(configKey)
        return configValue.toDto()
    }
}