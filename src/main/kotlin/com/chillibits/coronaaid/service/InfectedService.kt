package com.chillibits.coronaaid.service

import com.chillibits.coronaaid.events.InfectedChangeEvent
import com.chillibits.coronaaid.model.db.Infected
import com.chillibits.coronaaid.repository.InfectedRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InfectedService {

    @Autowired
    private lateinit var infectedRepository: InfectedRepository

    @Autowired
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    fun findById(infectedId: Int) = infectedRepository.findById(infectedId)
    fun findAllEagerly() = infectedRepository.findAllEagerly()
    fun findAllEagerly(infected: Set<Int>) = infectedRepository.findAllEagerly(infected)
    fun findAllLockedSince(timestamp: Long, configAutoResetOffset: Long, refreshInterval: Long) = infectedRepository.findAllLockedSince(timestamp, configAutoResetOffset, refreshInterval)

    @Transactional
    fun changeLockedState(infectedId: Int, timestamp: Long) {
        infectedRepository.changeLockedState(infectedId, timestamp)
        applicationEventPublisher.publishEvent(InfectedChangeEvent(this, setOf(infectedId)))
    }

}