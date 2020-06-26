package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.events.InfectedChangeEvent
import com.chillibits.coronaaid.exception.exception.InfectedNotFoundException
import com.chillibits.coronaaid.model.db.HistoryItem
import com.chillibits.coronaaid.model.dto.HistoryItemDto
import com.chillibits.coronaaid.model.dto.HistoryItemInsertDto
import com.chillibits.coronaaid.repository.HistoryRepository
import com.chillibits.coronaaid.repository.SymptomRepository
import com.chillibits.coronaaid.service.InfectedService
import com.chillibits.coronaaid.shared.toDto
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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
    private lateinit var infectedService: InfectedService

    @Autowired
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @GetMapping(
            path = ["/history"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Returns all history items")
    fun getAllHistoryItems(): Set<HistoryItemDto> = historyRepository.findAll().map { it.toDto() }.toSet()

    @GetMapping(
            path = ["/history/{infectedId}"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiResponses(
            ApiResponse(code = 404, message = "Infected not found")
    )
    @ApiOperation("Returns all history items for a specific person")
    fun getHistoryItemsForSinglePerson(@PathVariable infectedId: Int): Set<HistoryItemDto> {
        if(infectedService.findById(infectedId).isEmpty) throw InfectedNotFoundException(infectedId)
        return historyRepository.getHistoryItemsForPerson(infectedId).map { it.toDto() }.toSet()
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
    fun addHistoryItem(@RequestBody historyDto: HistoryItemInsertDto): HistoryItemDto {
        val infected = infectedService.findById(historyDto.infectedId).orElseThrow { InfectedNotFoundException(historyDto.infectedId) }

        // Fetch symptoms
        val symptoms = historyDto.symptoms?.map { symptomRepository.findById(it) }?.filter { it.isPresent }?.map { it.get() }?.toSet()

        // Construct DAO object
        val item = historyRepository.save(
                HistoryItem(
                    0,
                    infected,
                    historyDto.timestamp,
                    symptoms ?: emptySet(),
                    historyDto.status,
                    historyDto.personalFeeling,
                    historyDto.notes
                )
        )

        // Unlock infected
        infectedService.changeLockedState(infected.id, 0)

        // Notify SSE Task
        applicationEventPublisher.publishEvent(InfectedChangeEvent(this, setOf(infected.id)))

        // Return stored DTO
        return item.toDto()
    }

    @PutMapping(
            path = ["/history"],
            consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiResponses(
            ApiResponse(code = 404, message = "History item not found"),
            ApiResponse(code = 404, message = "Infected not found")
    )
    fun updateHistoryItem(@RequestBody historyDto: HistoryItemInsertDto): HistoryItemDto? {

        return null
    }
}