package com.chillibits.coronaaid.config

import com.chillibits.coronaaid.events.InfectedChangeEvent
import com.chillibits.coronaaid.events.SseDataPreparedEvent
import com.chillibits.coronaaid.model.dto.InfectedRealtimeDto
import com.chillibits.coronaaid.repository.ConfigRepository
import com.chillibits.coronaaid.repository.HistoryRepository
import com.chillibits.coronaaid.service.InfectedService
import com.chillibits.coronaaid.shared.ConfigKeys
import com.chillibits.coronaaid.model.mapper.toCompressed
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@Component
@Transactional
class RealtimeTask : Runnable {

    @Autowired
    private lateinit var infectedService: InfectedService

    @Autowired
    private lateinit var configRepository: ConfigRepository

    @Autowired
    private lateinit var historyRepository: HistoryRepository

    @Autowired
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    private var trackedInfected: MutableSet<Int> = Collections.synchronizedSet(mutableSetOf())
    private var trackedInfectedLock = ReentrantLock()

    override fun run() {
        trackedInfectedLock.withLock {
            val configResetOffset = (configRepository.findByConfigKey(ConfigKeys.CK_AUTO_RESET_OFFSET)?.configValue?.toLong() ?: 0) * 1000
            val realtimeRefreshInterval = (configRepository.findByConfigKey(ConfigKeys.CK_REALTIME_REFRESH_INTERVAL)?.configValue?.toLong() ?: 0) * 1000

            loadInfectedChanges(configResetOffset, realtimeRefreshInterval)

            val infected = infectedService.findAllEagerly(trackedInfected)
            val mapped = infected.map { it.toCompressed(configResetOffset) }.map {
                InfectedRealtimeDto(
                        it.id,
                        it.done,
                        it.lastUnsuccessfulCallToday != null,
                        it.lastUnsuccessfulCallTodayString,
                        it.locked
                )
            }.toSet()

            applicationEventPublisher.publishEvent(SseDataPreparedEvent(this, mapped))
            trackedInfected.clear()
        }
    }

    private fun loadInfectedChanges(configResetOffset: Long, realtimeRefresh: Long) {
        val timeFrame = System.currentTimeMillis() - realtimeRefresh

        val changed = historyRepository
                .getAllInfectedWithChangedHistorySince(timeFrame)
                .plus(infectedService.findAllLockedSince(timeFrame, configResetOffset, realtimeRefresh))

        trackedInfected.addAll(changed)
    }

    @EventListener
    fun onInfectedChange(event: InfectedChangeEvent) {
        trackedInfectedLock.withLock {
            trackedInfected.addAll(event.changedInfected)
        }
    }

}