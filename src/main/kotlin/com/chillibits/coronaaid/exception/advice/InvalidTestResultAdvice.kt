package com.chillibits.coronaaid.exception.advice

import com.chillibits.coronaaid.exception.exception.InvalidTestResultException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class InvalidTestResultAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidTestResultException::class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    fun handler(ex: InvalidTestResultException) = ex.message
}