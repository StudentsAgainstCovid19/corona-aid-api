package com.chillibits.coronaaid.repository

import com.chillibits.coronaaid.model.db.ConfigItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ConfigRepository: JpaRepository<ConfigItem, Int> {

    @Query("SELECT c FROM ConfigItem c WHERE c.configKey = ?1")
    fun findByConfigKey(configKey: String): ConfigItem?
}