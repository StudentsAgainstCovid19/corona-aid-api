package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.model.dto.SymptomDto
import com.chillibits.coronaaid.repository.SymptomRepository
import com.chillibits.coronaaid.model.mapper.toDto
import com.chillibits.coronaaid.shared.XmlDtdUrl
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "Symptoms Rest Endpoint", tags = ["symptom"])
class SymptomController {

    @Autowired
    private lateinit var symptomRepository: SymptomRepository

    @GetMapping(
            path = ["/symptom"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @XmlDtdUrl(url = "https://www.corona-aid-ka.de/dtd/symptom.dtd")
    fun getAllSymptoms(): Set<SymptomDto> = symptomRepository.findAll().map { it.toDto() }.toSet()
}