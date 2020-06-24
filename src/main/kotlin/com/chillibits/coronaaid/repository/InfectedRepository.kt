package com.chillibits.coronaaid.repository

import com.chillibits.coronaaid.model.db.Infected
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface InfectedRepository: JpaRepository<Infected, Int> {

    @EntityGraph(
            value= "Infected.loadAll",
            attributePaths = ["contactData", "tests", "historyItems", "initialDiseases"],
            type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT i FROM Infected i")
    fun findAllEagerly(): Set<Infected>

    @EntityGraph(
            value= "Infected.loadAll",
            attributePaths = ["contactData", "tests", "historyItems", "initialDiseases"],
            type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT i FROM Infected i WHERE i.id IN (:infected)")
    fun findAllEagerly(@Param("infected") infected: Set<Int>): Set<Infected>

    @Query("SELECT i.id FROM Infected i WHERE ((i.lockedTimestamp >= ?1) OR (i.lockedTimestamp + ?2 >= ?1))")
    fun findAllLockedSince(timestamp: Long, configAutoResetOffset: Long): Set<Int>

    @Modifying
    @Transactional
    @Query("UPDATE Infected i SET i.lockedTimestamp = ?2 WHERE i.id = ?1")
    fun changeLockedState(infectedId: Int, timestamp: Long)

    @Modifying
    @Transactional
    @Query("UPDATE Infected i SET i.lockedTimestamp = 0")
    fun resetLockingOfAllInfected()

}