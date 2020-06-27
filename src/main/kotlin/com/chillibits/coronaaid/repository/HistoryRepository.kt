package com.chillibits.coronaaid.repository

import com.chillibits.coronaaid.model.db.HistoryItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface HistoryRepository: JpaRepository<HistoryItem, Int> {

    @Query("SELECT it FROM HistoryItem it WHERE it.infectedId.id = ?1")
    fun getHistoryItemsForPerson(infectedId: Int): Set<HistoryItem>

    @Query("SELECT it.infectedId.id FROM HistoryItem it WHERE it.timestamp >= ?1")
    fun getAllInfectedWithChangedHistorySince(timestamp: Long): Set<Int>

    @Transactional
    @Modifying
    @Query("UPDATE HistoryItem it SET it.timestamp = :#{#item.timestamp}, it.status = :#{#item.status}, it.personalFeeling = :#{#item.personalFeeling}, it.notes = :#{#item.notes} WHERE it.id = :#{#item.id}")
    fun updateHistoryItem(item: HistoryItem): Int
}