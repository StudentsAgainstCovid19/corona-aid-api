package com.chillibits.coronaaid.config

import com.chillibits.coronaaid.events.InfectedChangeEvent
import com.chillibits.coronaaid.repository.ConfigRepository
import com.chillibits.coronaaid.repository.HistoryRepository
import com.chillibits.coronaaid.repository.InfectedRepository
import com.chillibits.coronaaid.shared.ConfigKeys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@Configuration
class CronJobsConfig {

    @Autowired
    private lateinit var infectedRepository: InfectedRepository

    @Autowired
    private lateinit var historyRepository: HistoryRepository

    @Autowired
    private lateinit var configRepository: ConfigRepository

    @Autowired
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

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
        val current = System.currentTimeMillis()
        val timeFrame = current - 15000L
        val offset = (configRepository.findByConfigKey(ConfigKeys.CK_AUTO_RESET_OFFSET)?.configValue?.toLong() ?: 0) * 1000

        val changed = historyRepository
                .getHistoryItemIdChangedSince(timeFrame)
                .plus(infectedRepository.findAllLockedSince(timeFrame, offset))
        val infected = infectedRepository.findAllEagerly(changed)

        applicationEventPublisher.publishEvent(InfectedChangeEvent(this, infected))
    }
}