package com.chillibits.coronaaid.model.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "test")
class Test {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int = 0

    // Foreign key to the affected infected
    @ManyToOne
    @JoinColumn(name = "infected_id")
    private val infectedId: Infected? = null

    // Time of scheduling, respectively the time of implementation of the test
    private val timestamp: Long = System.currentTimeMillis()

    // Test result - 0: Scheduled, 1: Positive, 2: Negative, 3: Invalid
    private val result: Int = RESULT_SCHEDULED

    companion object {
        // Constants
        const val RESULT_SCHEDULED = 0
        const val RESULT_POSITIVE = 1
        const val RESULT_NEGATIVE = 2
        const val RESULT_INVALID = 3
    }
}