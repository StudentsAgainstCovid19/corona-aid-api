package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.exception.exception.InfectedLockedException
import com.chillibits.coronaaid.exception.exception.InfectedNotFoundException
import com.chillibits.coronaaid.model.dto.InfectedDto
import com.chillibits.coronaaid.repository.ConfigRepository
import com.chillibits.coronaaid.repository.InfectedRepository
import com.chillibits.coronaaid.shared.ConfigKeys
import com.chillibits.coronaaid.shared.toDto
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "Infected REST Endpoint", tags = ["infected"])
class InfectedController {

    @Autowired
    private lateinit var infectedRepository: InfectedRepository
    @Autowired
    private lateinit var configRepository: ConfigRepository

    @GetMapping(
            path = ["/infected"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Returns all infected persons with all available attributes")
    fun getAllInfected(): List<InfectedDto> = infectedRepository.findAll().map { it.toDto() }

    @GetMapping(
            path = ["/infected/{infectedId}"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Get details of single infected (locks the infected for access)")
    fun getSingleInfected(@PathVariable infectedId: Int): InfectedDto? {
        val infected = infectedRepository.findById(infectedId).orElseThrow { InfectedNotFoundException(infectedId) }

        // Retrieve config record for 'autoResetOffset' (seconds -> conversion to millis)
        val autoResetOffset = configRepository.findByConfigKey(ConfigKeys.CK_AUTO_RESET_OFFSET).configValue.toInt()
        if(infected.lockedTimestamp > System.currentTimeMillis() - autoResetOffset * 1000)
            throw InfectedLockedException(infectedId)

        // Lock infected for other access
        infectedRepository.changeLockedState(infectedId, System.currentTimeMillis())

        return infected.toDto()
    }
}