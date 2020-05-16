package com.chillibits.coronaaid

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoronaAidApplication

fun main(args: Array<String>) {
	runApplication<CoronaAidApplication>(*args)
}