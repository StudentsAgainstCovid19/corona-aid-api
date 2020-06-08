package com.chillibits.coronaaid.repository

import com.chillibits.coronaaid.model.db.Test
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TestRepository: JpaRepository<Test, Int> {

    @Query("SELECT t FROM Test t WHERE t.infectedId.id = ?1")
    fun findTestsForPerson(infectedId: Int): List<Test>
}