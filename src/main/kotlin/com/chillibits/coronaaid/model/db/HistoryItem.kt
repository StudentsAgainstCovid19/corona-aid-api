package com.chillibits.coronaaid.model.db

import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.PastOrPresent
import javax.validation.constraints.Size

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
        @PastOrPresent(message = "Timestamp in the future not allowed")
        val timestamp: Long,

        // List of symptoms
        @ManyToMany(cascade = [CascadeType.ALL])
        @JoinTable(
                name = "history_symptoms",
                joinColumns = [JoinColumn(name = "history_item_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "symptom_id", referencedColumnName = "id")]
        )
        val symptoms: Set<Symptom>,

        // Status of the call - 0: Not reachable, 1: Reached, 2: Flatmate answered
        @Min(value = 0, message = "Status must be >= 0")
        @Max(value = 2, message = "Status must be <= 2")
        val status: Int,

        // Personal feeling (Rating from 0 - 9)
        @Min(value = 0, message = "Personal feeling must be >= 0")
        @Max(value = 10, message = "Personal feeling be <= 9")
        val personalFeeling: Int,

        //Notes about the infected person
        @Column(columnDefinition = "TEXT")
        val notes: String? = null

) {
    companion object {
        // Constants
        const val STATUS_NOT_REACHABLE = 0
        const val STATUS_REACHED = 1
        const val STATUS_FLATMATE_ANSWERED = 2
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as HistoryItem
        return id == other.id
    }

    override fun hashCode() = id
}