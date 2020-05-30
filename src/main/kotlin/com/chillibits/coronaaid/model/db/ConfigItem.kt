package com.chillibits.coronaaid.model.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "config")
class ConfigItem {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int = 0

    // Key of the key-value pair. List gets filtered by the key
    private val key: String = ""

    // Value of the key-value pair.
    private val value: String = ""
}