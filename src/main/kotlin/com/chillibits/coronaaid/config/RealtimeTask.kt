package com.chillibits.coronaaid.config

import com.chillibits.coronaaid.events.InfectedChangeEvent
import com.chillibits.coronaaid.events.SseDataPreparedEvent
import com.chillibits.coronaaid.model.dto.InfectedRealtimeDto
import com.chillibits.coronaaid.repository.ConfigRepository
import com.chillibits.coronaaid.repository.HistoryRepository
import com.chillibits.coronaaid.repository.InfectedRepository
import com.chillibits.coronaaid.shared.ConfigKeys
import com.chillibits.coronaaid.shared.toCompressed
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
    private lateinit var infectedRepository: InfectedRepository

    @Autowired
    private lateinit var configRepository: ConfigRepository

    @Autowired
    private lateinit var historyRepository: HistoryRepository

    @Autowired
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    private var trackedInfected: MutableSet<Int> = Collections.synchronizedSet(mutableSetOf())
    private var trackedInfectedLock = ReentrantLock()

    override fun run() {
        val offset = (configRepository.findByConfigKey(ConfigKeys.CK_AUTO_RESET_OFFSET)?.configValue?.toLong() ?: 0) * 1000
        loadInfectedChanges(offset)

        val infected = trackedInfectedLock.run {
            withLock {
                infectedRepository.findAllEagerly(trackedInfected)
            }
        }
        val mapped = infected.map { it.toCompressed(offset) }.map {
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

    private fun loadInfectedChanges(offset: Long) {
        val timeFrame = System.currentTimeMillis() - 15000L

        val changed = historyRepository
                .getAllInfectedWithChangedHistorySince(timeFrame)
                .plus(infectedRepository.findAllLockedSince(timeFrame, offset))

        trackedInfected.addAll(changed)
    }

    @EventListener
    public fun onInfectedChange(event: InfectedChangeEvent) {
        trackedInfectedLock.withLock {
            trackedInfected.addAll(event.changedInfected)
        }
    }

}