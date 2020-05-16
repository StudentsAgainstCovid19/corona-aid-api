package com.chillibits.coronaaid

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoronaAidApplication: CommandLineRunner {
	override fun run(vararg args: String?) {
		// Test db

	}

}

fun main(args: Array<String>) {
	runApplication<CoronaAidApplication>(*args)
}