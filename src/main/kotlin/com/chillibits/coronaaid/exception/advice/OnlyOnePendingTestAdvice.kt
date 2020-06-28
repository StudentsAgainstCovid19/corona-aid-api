package com.chillibits.coronaaid.exception.advice

import com.chillibits.coronaaid.exception.exception.OnlyOnePendingTestException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class OnlyOnePendingTestAdvice {

    @ResponseBody
    @ExceptionHandler(OnlyOnePendingTestException::class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    fun handler(ex: OnlyOnePendingTestException) = ex.message
}