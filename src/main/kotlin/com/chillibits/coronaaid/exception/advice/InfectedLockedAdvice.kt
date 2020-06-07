package com.chillibits.coronaaid.exception.advice

import com.chillibits.coronaaid.exception.exception.InfectedLockedException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class InfectedLockedAdvice {

    @ResponseBody
    @ExceptionHandler(InfectedLockedException::class)
    @ResponseStatus(HttpStatus.LOCKED)
    fun employeeNotFoundHandler(ex: InfectedLockedException) = ex.message
}