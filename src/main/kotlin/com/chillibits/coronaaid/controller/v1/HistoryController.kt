package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.exception.exception.InfectedNotFoundException
import com.chillibits.coronaaid.model.db.HistoryItem
import com.chillibits.coronaaid.model.db.Symptom
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
import org.springframework.web.bind.annotation.*

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
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE ]
    )
    @ApiOperation("Returns all history items")
    fun getAllHistoryItems(): List<HistoryItemDto> = historyRepository.findAll().map { it.toDto() }

    @GetMapping(
            path = ["/history/{infectedId}"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Returns all history items for a specific person")
    fun getHistoryItemForSinglePerson(@PathVariable infectedId: Int): List<HistoryItemDto>
            = historyRepository.getHistoryItemsForPerson(infectedId).map { it.toDto() } //TODO: Add infected not found check

    @PostMapping(
            path = ["/history"],
            consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Pushes a new history item to the database")
    @ApiResponses(
            ApiResponse(code = 404, message = "Infected not found")
    )
    fun addHistoryItem(@RequestBody historyDto: HistoryItemInsertDto): ResponseEntity<HistoryItemDto> {
        val infected = infectedRepository.findById(historyDto.infectedId).orElseThrow { InfectedNotFoundException(historyDto.infectedId) }

        //Lazy fetch symptoms
        val symptoms = mutableListOf<Symptom>()
        symptoms.addAll(symptomRepository.findAllById(historyDto.symptom)) //TODO: Null checks -> 404 Status code or throw exception

        //Construct DAO object
        val item = HistoryItem(
                0,
                infected,
                historyDto.timestamp,
                symptoms,
                historyDto.status,
                historyDto.personalFeeling
        )

        //Return saved DTO
        return ResponseEntity(historyRepository.save(item).toDto(), HttpStatus.CREATED)
    }

}