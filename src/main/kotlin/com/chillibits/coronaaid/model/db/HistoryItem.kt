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
data class HistoryItem (

        // Fields
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        // Foreign key to the affected infected
        @ManyToOne
        @JoinColumn(name = "infected_id")
        val infectedId: Infected? = null,

        // Timestamp of the call
        val timestamp: Long,

        // List of symptoms
        @ManyToMany(mappedBy = "historyItems")
        val symptoms: List<Symptom>,

        // Status of the call - 0: Not reachable, 1: Reached, 2: Flatmate answered
        val status: Int,

        // Personal feeling (Rating from 0 - 10)
        val personalFeeling: Int

        // TODO: Add more fields

) {
    companion object {
        // Constants
        const val STATUS_NOT_REACHABLE = 0
        const val STATUS_REACHED = 1
        const val STATUS_FLATMATE_ANSWERED = 2
    }
}