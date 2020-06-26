package com.chillibits.coronaaid.repository

import com.chillibits.coronaaid.model.db.Test
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface TestRepository: JpaRepository<Test, Int> {

    @Query("SELECT t FROM Test t WHERE t.infectedId.id = ?1")
    fun findTestsForPerson(infectedId: Int): Set<Test>

    @Transactional
    @Modifying
    @Query("UPDATE Test t SET t.result = ROUND(RAND() * 2, 0) + 1 WHERE t.result = 0")
    fun evaluateAllPendingTests()
}