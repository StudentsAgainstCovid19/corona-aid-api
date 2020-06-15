package com.chillibits.coronaaid.model.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "residential_group")
data class ResidentialGroup (

        // Fields
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        // List of infected persons
        @ManyToMany
        @JoinTable(
                name = "infected_group",
                joinColumns = [JoinColumn(name = "infected_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "group_id", referencedColumnName = "id")]
        )
        val infected: List<Infected>
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as ResidentialGroup

                if (id != other.id) return false

                return true
        }

        override fun hashCode(): Int {
                return id
        }
}