package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.model.dto.TestDto
import com.chillibits.coronaaid.repository.TestRepository
import com.chillibits.coronaaid.shared.toDto
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "Test REST Endpoint", tags = ["test"])
class TestController {

    @Autowired
    private lateinit var testRepository: TestRepository

    @RequestMapping(
            method = [RequestMethod.GET],
            path = ["/test"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE ]
    )
    fun getAllTests(): List<TestDto> = testRepository.findAll().map { it.toDto() }

    @RequestMapping(
            method = [RequestMethod.GET],
            path = ["/test/{infectedId}"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    fun getTestsForSinglePerson(@PathVariable infectedId: Int): List<TestDto>
            = testRepository.findTestsForPerson(infectedId).map { it.toDto()}
}