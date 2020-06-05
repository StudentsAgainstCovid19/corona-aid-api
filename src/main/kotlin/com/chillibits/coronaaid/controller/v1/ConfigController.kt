package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.model.dto.ConfigItemDto
import com.chillibits.coronaaid.repository.ConfigRepository
import com.chillibits.coronaaid.shared.toDto
import io.swagger.annotations.Api
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
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE ]
    )
    fun getAllConfigItems(): List<ConfigItemDto> = configRepository.findAll().map { it.toDto() }

    @GetMapping(
            path = ["/config/{configKey}"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE ]
    )
    fun getSingleConfigItem(@PathVariable configKey: String): ConfigItemDto = configRepository.findByConfigKey(configKey).toDto()
}