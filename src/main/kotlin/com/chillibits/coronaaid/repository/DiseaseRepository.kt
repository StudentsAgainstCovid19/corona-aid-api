package com.chillibits.coronaaid.repository

import com.chillibits.coronaaid.model.db.Disease
import org.springframework.data.jpa.repository.JpaRepository

interface DiseaseRepository: JpaRepository<Disease, Int> {

}