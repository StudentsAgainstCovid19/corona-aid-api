package com.chillibits.coronaaid.repository

import com.chillibits.coronaaid.model.db.ContactItem
import org.springframework.data.jpa.repository.JpaRepository

interface ContactRepository: JpaRepository<ContactItem, Int>