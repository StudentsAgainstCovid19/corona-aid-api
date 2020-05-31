package com.chillibits.coronaaid

import com.chillibits.coronaaid.model.db.Infected
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

	override fun run(vararg args: String?) {
		// Test db
		createInfected()
	}

	private fun createInfected() {
		val birthDate = GregorianCalendar(1985, Calendar.JUNE, 4).time
		val i = Infected(
				1,
				"John",
				"Doe",
				birthDate,
				"Karlsruhe",
				76131,
				"Erzbergerstraße",
				121,
				49.0264134,
				8.3831085,
				emptyList(),
				emptyList(),
				emptyList(),
				emptyList(),
				emptyList()
		)
		infectedRepository.save(i)
	}

}

fun main(args: Array<String>) {
	runApplication<CoronaAidApplication>(*args)
}