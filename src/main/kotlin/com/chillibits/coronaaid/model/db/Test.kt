package com.chillibits.coronaaid.model.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Entity
@Table(name = "test")
data class Test (

        // Fields
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        // Foreign key to the affected infected
        @ManyToOne
        @JoinColumn(name = "infected_id")
        val infectedId: Infected? = null,

        // Time of scheduling, respectively the time of implementation of the test
        val timestamp: Long,

        // Test result - 0: Scheduled, 1: Positive, 2: Negative, 3: Invalid
        @Min(value = 0, message = "Value must be >= 0")
        @Max(value = 3, message = "Value must be <= 3")
        val result: Int
) {
    companion object {
        // Constants
        const val RESULT_SCHEDULED = 0
        const val RESULT_POSITIVE = 1
        const val RESULT_NEGATIVE = 2
        const val RESULT_INVALID = 3
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Test
        return id == other.id
    }

    override fun hashCode() = id
}