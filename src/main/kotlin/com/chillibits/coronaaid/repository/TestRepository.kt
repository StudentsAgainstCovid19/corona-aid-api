package com.chillibits.coronaaid.repository

import com.chillibits.coronaaid.model.db.Test
import org.springframework.data.jpa.repository.JpaRepository

interface TestRepository: JpaRepository<Test, Int> {

}