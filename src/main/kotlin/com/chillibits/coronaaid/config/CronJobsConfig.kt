package com.chillibits.coronaaid.config

import com.chillibits.coronaaid.model.dto.InfectedRealtimeDto
import com.chillibits.coronaaid.repository.ConfigRepository
import com.chillibits.coronaaid.repository.HistoryRepository
import com.chillibits.coronaaid.repository.InfectedRepository
import com.chillibits.coronaaid.shared.ConfigKeys
import com.chillibits.coronaaid.shared.SseEmitterStorage
import com.chillibits.coronaaid.shared.toCompressed
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.annotation.Transactional
import java.io.StringWriter
import javax.annotation.PostConstruct

@Configuration
class CronJobsConfig {

    @Autowired
    private lateinit var infectedRepository: InfectedRepository

    @Autowired
    private lateinit var historyRepository: HistoryRepository

    @Autowired
    private lateinit var configRepository: ConfigRepository

    val log: Logger = LoggerFactory.getLogger(CronJobsConfig::class.java)

    @PostConstruct
    public fun onStartup() {
        log.info("Startup finished. Executing jobs now ...")



        log.info("Jobs finished.")
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public fun dailyReset() {
        log.info("Daily reset started.")
        infectedRepository.resetLockingOfAllInfected()
        log.info("Daily reset finished.")
    }

    @Scheduled(fixedDelay = 15000)
    @Transactional
    public fun sendInterval() {
        val offset = configRepository.findByConfigKey(ConfigKeys.CK_AUTO_RESET_OFFSET)?.configValue?.toLong() ?: 0

        val list = mutableSetOf<InfectedRealtimeDto>()
        val changed = historyRepository
                .getHistoryItemIdChangedSince(System.currentTimeMillis() - 15000)
                .plus(infectedRepository.findAllLockedSince(System.currentTimeMillis() - 15000))
        val infected = infectedRepository.findAllEagerly(changed)

        val writer = StringWriter()
        val mapper = ObjectMapper()
        mapper.writeValue(writer, infected.map { it.toCompressed(offset) }.map {
            InfectedRealtimeDto(
                    it.id,
                    it.done,
                    it.lastUnsuccessfulCallToday != null,
                    it.lastUnsuccessfulCallTodayString,
                    it.locked
            )
        })
        SseEmitterStorage.sendToAll(writer.toString())
    }
}