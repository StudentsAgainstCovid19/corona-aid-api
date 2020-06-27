package com.chillibits.coronaaid.exception.advice

import com.chillibits.coronaaid.exception.exception.NoHistoryItemForTodayException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class NoHistoryItemForTodayAdvice {

    @ResponseBody
    @ExceptionHandler(NoHistoryItemForTodayException::class)
    @ResponseStatus(HttpStatus.TOO_EARLY)
    fun handler(ex: NoHistoryItemForTodayException) = ex.message
}