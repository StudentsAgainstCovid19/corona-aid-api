package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.exception.exception.InfectedNotFoundException
import com.chillibits.coronaaid.model.db.Test
import com.chillibits.coronaaid.model.dto.TestDto
import com.chillibits.coronaaid.model.dto.TestInsertDto
import com.chillibits.coronaaid.repository.InfectedRepository
import com.chillibits.coronaaid.repository.TestRepository
import com.chillibits.coronaaid.shared.toDto
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

    @Autowired
    private lateinit var infectedRepository: InfectedRepository

    @GetMapping(
            path = ["/test"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Returns all tests")
    fun getAllTests(): Set<TestDto> = testRepository.findAll().map { it.toDto() }.toSet()

    @GetMapping(
            path = ["/test/{infectedId}"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Returns all tests for a specific person")
    fun getTestsForSinglePerson(@PathVariable infectedId: Int): Set<TestDto>
            = testRepository.findTestsForPerson(infectedId).map { it.toDto()}.toSet()

    @PostMapping(
            path = ["/test"],
            consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Pushes a new test to the database")
    fun addTest(@RequestBody testDto: TestInsertDto): TestDto? {
        val infected = infectedRepository.findById(testDto.infectedId).orElseThrow { InfectedNotFoundException(testDto.infectedId) }

        val item = testRepository.save(
                Test(
                    0,
                    infected,
                    testDto.timestamp,
                    testDto.result
        ))

        return item.toDto()
    }
}