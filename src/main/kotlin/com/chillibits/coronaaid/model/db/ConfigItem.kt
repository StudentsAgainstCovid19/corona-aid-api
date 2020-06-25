package com.chillibits.coronaaid.model.db

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "config")
data class ConfigItem (

        // Fields
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        // Key of the key-value pair. List gets filtered by the key
        @Column(unique = true)
        @NotNull(message = "Config key cannot be null")
        @NotBlank(message = "Config key cannot be blank")
        val configKey: String,

        // Value of the key-value pair (do not name this field 'value'. This would cause an MySQL error)
        @NotNull(message = "Config value cannot be null")
        @NotBlank(message = "Config value cannot be blank")
        val configValue: String
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false
                other as ConfigItem
                return id == other.id
        }

        override fun hashCode() = id
}