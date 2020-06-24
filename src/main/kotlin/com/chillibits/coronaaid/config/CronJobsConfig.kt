package com.chillibits.coronaaid.config

import com.chillibits.coronaaid.repository.ConfigRepository
import com.chillibits.coronaaid.repository.InfectedRepository
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
    private lateinit var configRepository: ConfigRepository

    @Autowired
    private lateinit var taskScheduler: TaskScheduler

    @Autowired
    private lateinit var realtimeTask: RealtimeTask

    val log: Logger = LoggerFactory.getLogger(CronJobsConfig::class.java)

    @PostConstruct
    public fun onStartup() {
        log.info("Startup finished. Executing jobs now ...")

        val configAutoResetOffset = configRepository
                .findByConfigKey(ConfigKeys.CK_AUTO_RESET_OFFSET)?.configValue?.toLong() ?: ConfigKeys.CK_AUTO_RESET_OFFSET_DEFAULT.toLong()

        taskScheduler.schedule(Runnable { dailyReset() }, CronTrigger("0 0 0 * * ?"))
        taskScheduler.scheduleAtFixedRate(realtimeTask, Duration.ofSeconds(configAutoResetOffset))

        log.info("Jobs finished.")
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public fun dailyReset() {
        log.info("Daily reset started.")
        infectedRepository.resetLockingOfAllInfected()
        log.info("Daily reset finished.")
    }

}