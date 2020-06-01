package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.model.db.Infected
import com.chillibits.coronaaid.model.dto.InfectedDto
import com.chillibits.coronaaid.repository.InfectedRepository
import io.swagger.annotations.Api
import org.modelmapper.ModelMapper
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

    @Autowired
    private lateinit var mapper: ModelMapper

    @RequestMapping(
            method = [RequestMethod.GET],
            path = ["/infected"],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getAllInfected(): List<Infected> {
        //return infectedRepository.findAll().map { convertToDto(it) }
        return infectedRepository.findAll()
    }



    // ---------------------------------------------- Utility functions ------------------------------------------------
    private fun convertToDto(infected: Infected) = mapper.map(infected, InfectedDto::class.java)
}