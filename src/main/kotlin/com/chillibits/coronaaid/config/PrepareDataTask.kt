package com.chillibits.coronaaid.config

import com.chillibits.coronaaid.model.db.HistoryItem
import com.chillibits.coronaaid.model.db.Infected
import com.chillibits.coronaaid.model.db.Symptom
import com.chillibits.coronaaid.model.db.Test
import com.chillibits.coronaaid.repository.HistoryRepository
import com.chillibits.coronaaid.repository.InfectedRepository
import com.chillibits.coronaaid.repository.SymptomRepository
import com.chillibits.coronaaid.repository.TestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Component
@Transactional
class PrepareDataTask : Runnable {

    @Autowired
    private lateinit var infectedRepository: InfectedRepository

    @Autowired
    private lateinit var symptomRepository: SymptomRepository

    @Autowired
    private lateinit var historyRepository: HistoryRepository

    @Autowired
    private lateinit var testRepository: TestRepository

    private var randomizer = Random(System.currentTimeMillis())
    private lateinit var symptoms: List<Symptom>
    private lateinit var infected: List<Infected>

    private val bufferedHistoryItems = mutableListOf<HistoryItem>()
    private val bufferedTests = mutableListOf<Test>()

    override fun run() {
        initData()
        prepareData()
        pushData()
    }

    private fun initData() {
        bufferedHistoryItems.clear()
        bufferedTests.clear()

        symptoms = symptomRepository.findAll()
        infected = infectedRepository.findAllEagerly().filter { !it.done }
    }

    private fun prepareData() {
        for (inf in infected) {
            if(Math.random() < REL_AMOUNT_CALLS) {
                addHistoryItem(inf)
                addTest(inf)
            }
        }
    }

    private fun pushData() {
        historyRepository.saveAll(bufferedHistoryItems)
        testRepository.saveAll(bufferedTests)
    }

    private fun addHistoryItem(infected: Infected) {
        var symptomsInsert = emptySet<Symptom>()
        var personalFeelingInsert = 0
        var statusInsert = 0

        if(Math.random() < SUCCESS_PROBABILITY) {
            symptomsInsert = symptoms.filter { Math.random() < (it.probability / 100.0) }.toSet()
            personalFeelingInsert =  randomizer.nextInt(1, 5)
            statusInsert = 1
        }

        val historyItem = HistoryItem(
                0,
                infected,
                System.currentTimeMillis(),
                symptomsInsert,
                statusInsert,
                personalFeelingInsert,
                null
        )

        bufferedHistoryItems.add(historyItem)
    }

    private fun addTest(infected: Infected) {
        if(Math.random() > TEST_PROBABILITY) {
            return
        }

        val test = Test(
                0,
                infected,
                System.currentTimeMillis(),
                0
        )

        bufferedTests.add(test)
    }

    companion object {

        const val REL_AMOUNT_CALLS = 0.5
        const val TEST_PROBABILITY = 0.1
        const val SUCCESS_PROBABILITY = 0.8

    }

}