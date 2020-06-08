package com.chillibits.coronaaid.repository

import com.chillibits.coronaaid.model.db.HistoryItem
import com.chillibits.coronaaid.model.dto.HistoryItemDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface HistoryRepository: JpaRepository<HistoryItem, Int> {

    @Query("SELECT it FROM HistoryItem it WHERE it.infectedId.id = ?1")
    fun getHistoryItemsForPerson(infectedId : Int): List<HistoryItem>

}