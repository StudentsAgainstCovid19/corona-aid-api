package com.chillibits.coronaaid.repository

import com.chillibits.coronaaid.model.db.ResidentialGroup
import org.springframework.data.jpa.repository.JpaRepository

interface ResidentialGroupRepository: JpaRepository<ResidentialGroup, Int> {

}