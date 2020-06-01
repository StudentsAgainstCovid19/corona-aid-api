package com.chillibits.coronaaid.repository

import com.chillibits.coronaaid.model.db.Symptom
import org.springframework.data.jpa.repository.JpaRepository

interface SymptomRepository: JpaRepository<Symptom, Int> {

}