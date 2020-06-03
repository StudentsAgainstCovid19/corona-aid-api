package com.chillibits.coronaaid.model.db

import com.fasterxml.jackson.annotation.JsonBackReference
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
class ResidentialGroup (

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
        @JsonBackReference
        val infected: List<Infected>
)