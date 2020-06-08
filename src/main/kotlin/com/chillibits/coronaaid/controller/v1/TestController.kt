package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.model.db.Test
import com.chillibits.coronaaid.model.dto.TestDto
import com.chillibits.coronaaid.repository.TestRepository
import com.chillibits.coronaaid.shared.toDto
import com.chillibits.coronaaid.shared.toModel
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "Test REST Endpoint", tags = ["test"])
class TestController {

    @Autowired
    private lateinit var testRepository: TestRepository

    @GetMapping(
            path = ["/test"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE ]
    )
    @ApiOperation("Returns all tests")
    fun getAllTests(): List<TestDto> = testRepository.findAll().map { it.toDto() }

    @GetMapping(
            path = ["/test/{infectedId}"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Returns all tests for a specific person")
    fun getTestsForSinglePerson(@PathVariable infectedId: Int): List<TestDto>
            = testRepository.findTestsForPerson(infectedId).map { it.toDto()}

    @PostMapping(
            path = ["/test"],
            consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Pushes a new test to the database")
    fun addTest(@RequestBody testDto: TestDto): Test? = testRepository.save(testDto.toModel())
}