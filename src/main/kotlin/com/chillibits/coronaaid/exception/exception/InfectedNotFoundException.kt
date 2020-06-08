package com.chillibits.coronaaid.exception.exception

class InfectedNotFoundException(infectedId: Int):
        RuntimeException("Infected with the id $infectedId not found.")