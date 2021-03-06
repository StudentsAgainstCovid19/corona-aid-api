package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.exception.exception.InfectedLockedException
import com.chillibits.coronaaid.exception.exception.InfectedNotFoundException
import com.chillibits.coronaaid.model.dto.InfectedDto
import com.chillibits.coronaaid.repository.ConfigRepository
import com.chillibits.coronaaid.service.InfectedService
import com.chillibits.coronaaid.shared.ConfigKeys
import com.chillibits.coronaaid.shared.ConfigKeys.CK_AUTO_RESET_OFFSET_DEFAULT
import com.chillibits.coronaaid.model.mapper.toCompressed
import com.chillibits.coronaaid.model.mapper.toDto
import com.chillibits.coronaaid.shared.XmlDtdUrl
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "Infected REST Endpoint", tags = ["infected"])
class InfectedController {

    @Autowired
    private lateinit var infectedService: InfectedService

    @Autowired
    private lateinit var configRepository: ConfigRepository

    @GetMapping(
            path = ["/infected"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Returns all infected persons with all available attributes")
    fun getAllInfected(): Set<Any> = infectedService.findAllEagerly().map { it.toDto() }.toSet()

    @GetMapping(
            path = ["/infected"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
            params = ["compress"]
    )
    @ApiOperation("Returns all infected persons with all available attributes")
    @XmlDtdUrl(url = "https://www.corona-aid-ka.de/dtd/infected_compressed.dtd", rootElement = "Set")
    fun getAllInfectedCompressed(): Set<Any> {
        val configResetOffset = configRepository.findByConfigKey(ConfigKeys.CK_AUTO_RESET_OFFSET)?.configValue?.toLong()
                ?: CK_AUTO_RESET_OFFSET_DEFAULT.toLong()
        return infectedService.findAllEagerly().map { it.toCompressed(configResetOffset * 1000) }.toSet()
    }

    @GetMapping(
            path = ["/infected/{infectedId}"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Get details of single infected (locks the infected for access)")
    @ApiResponses(
            ApiResponse(code = 404, message = "Infected not found"),
            ApiResponse(code = 423, message = "Infected locked")
    )
    @XmlDtdUrl(url = "https://www.corona-aid-ka.de/dtd/infected_id.dtd", rootElement = "InfectedDto")
    fun getSingleInfected(@PathVariable infectedId: Int): InfectedDto? {
        val infected = infectedService.findById(infectedId).orElseThrow { InfectedNotFoundException(infectedId) }

        // Retrieve config record for 'autoResetOffset' (seconds -> conversion to millis)
        configRepository.findByConfigKey(ConfigKeys.CK_AUTO_RESET_OFFSET)?.let {
            if(infected.lockedTimestamp > System.currentTimeMillis() - it.configValue.toInt() * 1000)
                throw InfectedLockedException(infectedId)
        }

        // Lock infected for other access
        infectedService.changeLockedState(infectedId, System.currentTimeMillis())

        return infected.toDto()
    }

    @PutMapping(
            path = ["/infected/lock/{infectedId}"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Lock a unlocked infected")
    @ApiResponses(
            ApiResponse(code = 404, message = "Infected not found")
    )
    fun lockSingleInfected(@PathVariable infectedId: Int) = System.currentTimeMillis().apply {
        infectedService.changeLockedState(infectedId, this)
        infectedService.findById(infectedId).orElseThrow { InfectedNotFoundException(infectedId) }.toDto()
    }

    @PutMapping(
            path = ["/infected/unlock/{infectedId}"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Unlock a locked infected")
    @ApiResponses(
            ApiResponse(code = 404, message = "Infected not found")
    )
    fun unlockSingleInfected(@PathVariable infectedId: Int) {
        infectedService.changeLockedState(infectedId, 0)
        infectedService.findById(infectedId).orElseThrow { InfectedNotFoundException(infectedId) }.toDto()
    }
}