package com.chillibits.coronaaid.repository

import com.chillibits.coronaaid.model.db.Infected
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface InfectedRepository: JpaRepository<Infected, Int> {

    @Modifying
    @Transactional
    @Query("UPDATE Infected i SET i.locked = ?2, i.lockedLastUpdate = ?3 WHERE i.id = ?1")
    fun changeLockedState(infectedId: Int, locked: Boolean, timestamp: Long)

    @Modifying
    @Transactional
    @Query("UPDATE Infected i SET i.locked = false")
    fun resetLockingOfAllInfected()
}