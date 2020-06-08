package com.chillibits.coronaaid.config

import com.chillibits.coronaaid.repository.InfectedRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import javax.annotation.PostConstruct

@Configuration
class CronJobs {

    @Autowired
    private lateinit var infectedRepository: InfectedRepository

    val log: Logger = LoggerFactory.getLogger(CronJobs::class.java)

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
}