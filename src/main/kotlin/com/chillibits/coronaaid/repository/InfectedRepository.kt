package com.chillibits.coronaaid.repository

import com.chillibits.coronaaid.model.db.Infected
import org.springframework.data.jpa.repository.JpaRepository

interface InfectedRepository: JpaRepository<Infected, Int>