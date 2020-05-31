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
class ContactItem (

        // Fields
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private val id: Int = 0,

        // Foreign key to the affected infected
        @ManyToOne
        @JoinColumn(name = "infected_id")
        private val infectedId: Infected?,

        // Key of the key-value pair. List gets filtered by the key
        private val contactKey: String,

        // Value of the key-value pair (do not name this field 'value'. This would cause an MySQL error)
        private val contactValue: String
)