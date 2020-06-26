package com.chillibits.coronaaid.config

import com.chillibits.coronaaid.repository.ConfigRepository
import com.chillibits.coronaaid.repository.InfectedRepository
import com.chillibits.coronaaid.repository.TestRepository
import com.chillibits.coronaaid.shared.ConfigKeys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.support.CronTrigger
import java.time.Duration
import javax.annotation.PostConstruct

@Configuration
class CronJobsConfig {

    @Autowired
    private lateinit var infectedRepository: InfectedRepository

    @Autowired
    private lateinit var testRepository: TestRepository

    @Autowired
    private lateinit var configRepository: ConfigRepository

    @Autowired
    private lateinit var taskScheduler: TaskScheduler

    @Autowired
    private lateinit var realtimeTask: RealtimeTask

    val log: Logger = LoggerFactory.getLogger(CronJobsConfig::class.java)

    @PostConstruct
    fun onStartup() {
        log.info("Startup finished. Executing jobs now ...")

        val realtimeRefreshInterval = configRepository
                .findByConfigKey(ConfigKeys.CK_REALTIME_REFRESH_INTERVAL)?.configValue?.toLong() ?: ConfigKeys.CK_REALTIME_REFRESH_INTERVAL_DEFAULT.toLong()

        taskScheduler.schedule(Runnable { dailyReset() }, CronTrigger("0 0 0 * * ?"))
        taskScheduler.scheduleAtFixedRate(realtimeTask, Duration.ofSeconds(realtimeRefreshInterval))

        log.info("Jobs finished.")
    }

    @Scheduled(cron = "0 0 0 * * ?")
    fun dailyReset() {
        log.info("Daily reset started.")

        // Unlock all infected persons
        infectedRepository.resetLockingOfAllInfected()

        // Evaluate all pending tests
        testRepository.evaluateAllPendingTests()

        log.info("Daily reset finished.")
    }
}