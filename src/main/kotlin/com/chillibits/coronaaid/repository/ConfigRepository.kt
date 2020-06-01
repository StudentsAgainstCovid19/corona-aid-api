package com.chillibits.coronaaid.repository

import com.chillibits.coronaaid.model.db.ConfigItem
import org.springframework.data.jpa.repository.JpaRepository

interface ConfigRepository: JpaRepository<ConfigItem, Int> {

}