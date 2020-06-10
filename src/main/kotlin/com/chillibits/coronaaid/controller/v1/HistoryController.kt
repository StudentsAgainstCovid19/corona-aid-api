package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.exception.exception.InfectedNotFoundException
import com.chillibits.coronaaid.model.db.HistoryItem
import com.chillibits.coronaaid.model.dto.HistoryItemDto
import com.chillibits.coronaaid.model.dto.HistoryItemInsertDto
import com.chillibits.coronaaid.repository.HistoryRepository
import com.chillibits.coronaaid.repository.InfectedRepository
import com.chillibits.coronaaid.repository.SymptomRepository
import com.chillibits.coronaaid.shared.toDto
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "History REST Endpoint", tags = ["history"])
class HistoryController {

    @Autowired
    private lateinit var historyRepository: HistoryRepository

    @Autowired
    private lateinit var symptomRepository: SymptomRepository

    @Autowired
    private lateinit var infectedRepository: InfectedRepository

    @GetMapping(
            path = ["/history"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Returns all history items")
    fun getAllHistoryItems(): List<HistoryItemDto> = historyRepository.findAll().map { it.toDto() }

    @GetMapping(
            path = ["/history/{infectedId}"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiResponses(
            ApiResponse(code = 404, message = "Infected not found")
    )
    @ApiOperation("Returns all history items for a specific person")
    fun getHistoryItemsForSinglePerson(@PathVariable infectedId: Int): List<HistoryItemDto> {
        if(infectedRepository.findById(infectedId).isEmpty) throw InfectedNotFoundException(infectedId)
        return historyRepository.getHistoryItemsForPerson(infectedId).map { it.toDto() }
    }

    @PostMapping(
            path = ["/history"],
            consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiResponses(
            ApiResponse(code = 404, message = "Infected not found")
    )
    @ApiOperation("Pushes a new history item to the database")
    fun addHistoryItem(@RequestBody historyDto: HistoryItemInsertDto): ResponseEntity<HistoryItemDto> {
        val infected = infectedRepository.findById(historyDto.infectedId).orElseThrow { InfectedNotFoundException(historyDto.infectedId) }

        // Fetch symptoms
        val symptoms = historyDto.symptom.map { symptomRepository.findById(it) }.filter { it.isPresent }.map { it.get() }

        // Construct DAO object
        val item = historyRepository.save(
                HistoryItem(
                    0,
                    infected,
                    historyDto.timestamp,
                    symptoms,
                    historyDto.status,
                    historyDto.personalFeeling,
                    historyDto.notes
                )
        )

        // Unlock infected
        infectedRepository.changeLockedState(infected.id, System.currentTimeMillis())

        // Return stored DTO
        return ResponseEntity(item.toDto(), HttpStatus.CREATED)
    }

}