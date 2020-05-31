package com.chillibits.coronaaid

import com.chillibits.coronaaid.model.db.ContactItem
import com.chillibits.coronaaid.model.db.Infected
import com.chillibits.coronaaid.repository.ContactRepository
import com.chillibits.coronaaid.repository.InfectedRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class CoronaAidApplication: CommandLineRunner {

	@Autowired
	private lateinit var infectedRepository: InfectedRepository
	@Autowired
	private lateinit var contactRepository: ContactRepository

	override fun run(vararg args: String?) {
		// Test db
		createInfected()
	}

	private fun createInfected() {
		val birthDate = GregorianCalendar(1985, Calendar.JUNE, 4).time
		// Insert infected person
		val infected = infectedRepository.save(
				Infected(
						forename = "John",
						surname = "Doe",
						birthDate = birthDate,
						city = "Karlsruhe",
						postalCode = 76131,
						street = "Erzbergerstraße",
						houseNumber = 121,
						lat = 49.0264134,
						lon = 8.3831085
				)
		)

		// Insert its contact data
		contactRepository.save(ContactItem(infectedId = infected, contactKey = "phone", contactValue = "01234 5678990"))
		contactRepository.save(ContactItem(infectedId = infected, contactKey = "email", contactValue = "john.doe@dh-karlsruhe.de"))
	}

}

fun main(args: Array<String>) {
	runApplication<CoronaAidApplication>(*args)
}