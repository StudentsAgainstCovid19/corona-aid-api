package com.chillibits.coronaaid.model.db

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "config")
class ConfigItem (

        // Fields
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private val id: Int,

        // Key of the key-value pair. List gets filtered by the key
        @Column(unique = true)
        private val configKey: String,

        // Value of the key-value pair (do not name this field 'value'. This would cause an MySQL error)
        private val configValue: String
)