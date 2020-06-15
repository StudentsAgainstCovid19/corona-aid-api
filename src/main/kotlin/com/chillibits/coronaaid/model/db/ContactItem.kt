package com.chillibits.coronaaid.model.db

import javax.persistence.*

@Entity
@Table(name = "contact")
data class ContactItem (

        // Fields
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,

        // Foreign key to the affected infected
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "infected_id")
        val infectedId: Infected? = null,

        // Key of the key-value pair. List gets filtered by the key
        val contactKey: String,

        // Value of the key-value pair (do not name this field 'value'. This would cause an MySQL error)
        val contactValue: String
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false
                other as HistoryItem
                return id == other.id
        }

        override fun hashCode() = id
}