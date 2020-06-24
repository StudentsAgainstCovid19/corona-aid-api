package com.chillibits.coronaaid.repository

import com.chillibits.coronaaid.model.db.Infected
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface InfectedRepository: JpaRepository<Infected, Int> {

    @EntityGraph(
            value= "Infected.loadAll",
            attributePaths = ["contactData", "tests", "historyItems", "initialDiseases"],
            type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT i FROM Infected i")
    fun findAllEagerly(): Set<Infected>

    @Modifying
    @Transactional
    @Query("UPDATE Infected i SET i.lockedTimestamp = ?2 WHERE i.id = ?1")
    fun changeLockedState(infectedId: Int, timestamp: Long)

    @Modifying
    @Transactional
    @Query("UPDATE Infected i SET i.lockedTimestamp = 0")
    fun resetLockingOfAllInfected()

}