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

    /*
               <------- REFRESH INTERVAL ------->
    TIMESTAMP |----------------------------------| CURRENT TIME

    1) i.lockedTimestamp >= TIMESTAMP: Select all infected whose locked timestamp changed during the last #REFRESH INTERVAL# milliseconds
    2) (i.lockedTimestamp + CONFIGRESET >= TIMESTAMP) AND (i.lockedTimestamp + CONFIGRESET <= TIMESTAMP + REFRESH): Select all infected who were unlocked during the last #REFRESH INTERVAL# milliseconds
     */
    @Query("SELECT i.id FROM Infected i WHERE ((i.lockedTimestamp >= ?1) OR ((i.lockedTimestamp + ?2 >= ?1) AND (i.lockedTimestamp + ?2 <= ?1 + ?3)))")
    fun findAllLockedSince(timestamp: Long, configAutoResetOffset: Long, refreshInterval: Long): Set<Int>

    @Modifying
    @Transactional
    @Query("UPDATE Infected i SET i.lockedTimestamp = ?2 WHERE i.id = ?1")
    fun changeLockedState(infectedId: Int, timestamp: Long)
}