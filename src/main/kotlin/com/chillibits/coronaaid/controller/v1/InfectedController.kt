package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.model.dto.InfectedDto
import com.chillibits.coronaaid.repository.InfectedRepository
import com.chillibits.coronaaid.shared.toDto
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "Infected REST Endpoint", tags = ["infected"])
class InfectedController {

    @Autowired
    private lateinit var infectedRepository: InfectedRepository

    @RequestMapping(
            method = [RequestMethod.GET],
            path = ["/infected"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE ]
    )
    fun getAllInfected(): List<InfectedDto> = infectedRepository.findAll().map { it.toDto() }
}