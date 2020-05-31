package com.chillibits.coronaaid.model.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "history")
class HistoryItem (

        // Fields
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private val id: Int,

        // Foreign key to the affected infected
        @ManyToOne
        @JoinColumn(name = "infected_id")
        private val infectedId: Infected,

        // List of symptoms
        @ManyToMany(mappedBy = "historyItems")
        private val symptoms: List<Symptom>,

        // Timestamp of the call
        private val timestamp: Long,

        // Status of the call - 0: Not reachable, 1: Reached, 2: Flatmate answered
        private val status: Int,

        // Personal feeling (Rating from 0 - 10)
        private val personalFeeling: Int

        // TODO: Add more fields

) {
    companion object {
        // Constants
        const val STATUS_NOT_REACHABLE = 0
        const val STATUS_REACHED = 1
        const val STATUS_FLATMATE_ANSWERED = 2
    }
}