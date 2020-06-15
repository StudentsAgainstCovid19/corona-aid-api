package com.chillibits.coronaaid.model.db

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "config")
data class ConfigItem (

        // Fields
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        // Key of the key-value pair. List gets filtered by the key
        @Column(unique = true)
        val configKey: String,

        // Value of the key-value pair (do not name this field 'value'. This would cause an MySQL error)
        val configValue: String
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as ConfigItem

                if (id != other.id) return false

                return true
        }

        override fun hashCode(): Int {
                return id
        }
}