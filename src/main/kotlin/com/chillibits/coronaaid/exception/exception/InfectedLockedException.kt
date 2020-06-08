package com.chillibits.coronaaid.exception.exception

class InfectedLockedException(infectedId: Int):
        RuntimeException("The infected with the id $infectedId is locked. Reason for that might be a running edit process.")