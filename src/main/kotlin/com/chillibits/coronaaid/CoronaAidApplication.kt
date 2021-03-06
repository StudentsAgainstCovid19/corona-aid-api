package com.chillibits.coronaaid

import com.chillibits.coronaaid.model.db.ContactItem
import com.chillibits.coronaaid.model.db.Infected
import com.chillibits.coronaaid.repository.ContactRepository
import com.chillibits.coronaaid.repository.InfectedRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.LocalDate
import java.time.Month

val allowedOrigins = setOf(
		"http://localhost:63342",
		"http://localhost:63343",
		"https://sac19.jatsqi.com",
		"https://dev.sac19.jatsqi.com",
		"https://www.corona-aid-ka.de",
		"https://dev.corona-aid-ka.de"
)

@SpringBootApplication
@EnableScheduling
@EnableCaching
class CoronaAidApplication: CommandLineRunner {

	@Autowired
	private lateinit var infectedRepository: InfectedRepository

	@Autowired
	private lateinit var contactRepository: ContactRepository

	override fun run(vararg args: String?) {
		// Test db
		// createInfected()
	}

	private fun createInfected() {
		val birthDate = LocalDate.of(1985, Month.JUNE, 4)
		// Insert infected person
		val infected = infectedRepository.save(
				Infected(
						forename = "John",
						surname = "Doe",
						birthDate = birthDate,
						city = "Karlsruhe",
						postalCode = "76131",
						street = "Erzbergerstraße",
						houseNumber = "121",
						lat = 49.0264134,
						lon = 8.3831085,
						lockedTimestamp = System.currentTimeMillis(),
						healthInsuranceNumber = "M1234567"
				)
		)

		// Insert its contact data
		contactRepository.save(ContactItem(infectedId = infected, contactKey = "phone", contactValue = "01234 5678990"))
		contactRepository.save(ContactItem(infectedId = infected, contactKey = "email", contactValue = "john.doe@dh-karlsruhe.de"))
	}

	@Bean
	fun corsConfiguration(): WebMvcConfigurer {
		return object : WebMvcConfigurer {
			override fun addCorsMappings(registry: CorsRegistry) {
				registry.addMapping("/**")
						.allowedMethods("GET", "HEAD", "POST", "PUT")
						.allowedOrigins(*allowedOrigins.toTypedArray())
			}
		}
	}
}

fun main() {
	runApplication<CoronaAidApplication>()
}
