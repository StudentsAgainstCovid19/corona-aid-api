package com.chillibits.coronaaid.repository

import com.chillibits.coronaaid.model.db.HistoryItem
import org.springframework.data.jpa.repository.JpaRepository

interface HistoryRepository: JpaRepository<HistoryItem, Int>