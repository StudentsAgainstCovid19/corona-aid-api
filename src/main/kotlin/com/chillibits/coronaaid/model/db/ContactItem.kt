package com.chillibits.coronaaid.model.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "contact")
data class ContactItem (

        // Fields
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,

        // Foreign key to the affected infected
        @ManyToOne
        @JoinColumn(name = "infected_id")
        val infectedId: Infected? = null,

        // Key of the key-value pair. List gets filtered by the key
        val contactKey: String,

        // Value of the key-value pair (do not name this field 'value'. This would cause an MySQL error)
        val contactValue: String
)