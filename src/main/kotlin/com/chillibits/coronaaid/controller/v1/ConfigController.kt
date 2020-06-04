package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.repository.ConfigRepository
import com.chillibits.coronaaid.shared.toDto
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "Config REST Endpoint", tags = ["config"])
class ConfigController {

    @Autowired
    private lateinit var configRepository: ConfigRepository

    @RequestMapping(
            method = [RequestMethod.GET],
            path = ["/config"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE ]
    )
    fun getAllConfigItems() = configRepository.findAll().map { it.toDto() }

    @RequestMapping(
            method = [RequestMethod.GET],
            path = ["/config/{configKey}"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE ]
    )
    fun getSingleConfigItem(@PathVariable configKey: String) = configRepository.findByConfigKey(configKey).toDto()
}