package com.chillibits.coronaaid.exception.exception

class InvalidTestResultException:
        RuntimeException("You only can post tests with result value 0. Tests are getting evaluated from the official side.")