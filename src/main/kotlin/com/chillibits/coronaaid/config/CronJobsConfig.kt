package com.chillibits.coronaaid.config

import com.chillibits.coronaaid.model.db.HistoryItem
import com.chillibits.coronaaid.model.db.Symptom
import com.chillibits.coronaaid.repository.ConfigRepository
import com.chillibits.coronaaid.repository.HistoryRepository
import com.chillibits.coronaaid.repository.InfectedRepository
import com.chillibits.coronaaid.repository.SymptomRepository
import com.chillibits.coronaaid.repository.TestRepository
import com.chillibits.coronaaid.service.InfectedService
import com.chillibits.coronaaid.shared.ConfigKeys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.Scheduled
import java.time.Duration
import javax.annotation.PostConstruct
import kotlin.random.Random

@Configuration
class CronJobsConfig {

    @Autowired
    private lateinit var testRepository: TestRepository

    @Autowired
    private lateinit var configRepository: ConfigRepository

    @Autowired
    private lateinit var infectedService: InfectedService

    @Autowired
    private lateinit var symptomRepository: SymptomRepository

    @Autowired
    private lateinit var historyRepository: HistoryRepository

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

        taskScheduler.scheduleAtFixedRate(realtimeTask, Duration.ofSeconds(realtimeRefreshInterval))

        log.info("Jobs finished.")
    }
  
    @Scheduled(cron = "0 0 0 * * ?")
    fun dailyEvaluation() {
        log.info("Daily cron started.")

        // Evaluate all pending tests
        testRepository.evaluateAllPendingTests()

        // Generate fake history items for test purposes
        generateFakeHistoryItems()

        log.info("Daily cron finished.")
    }

    private fun generateFakeHistoryItems() {
        // Get random infected
        val infected = infectedService.findAllEagerly()
        val randomInfected = infected.filterIndexed { idx, _ -> idx % 2 == 0 }

        // Load all symptoms
        //val symptoms = symptomRepository.findAll()

        randomInfected.forEach {
            // Generate set of 0 to 5 random symptoms
            //val symptomSet = symptoms.filter { rand(1, 5) == 4 }.toSet()
            // Random status
            val status = rand(0, 2)
            // Random personal feeling
            val personalFeeling = rand(1, 5)
            // Create history item
            historyRepository.save(HistoryItem(0, it, System.currentTimeMillis(), emptySet(), status, personalFeeling))
        }
    }

    private fun rand(start: Int, end: Int) = Random(System.nanoTime()).nextInt(end - start + 1) + start
}
