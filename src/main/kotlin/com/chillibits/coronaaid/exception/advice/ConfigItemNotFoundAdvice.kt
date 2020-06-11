package com.chillibits.coronaaid.exception.advice

import com.chillibits.coronaaid.exception.exception.ConfigItemNotFoundException
import com.chillibits.coronaaid.exception.exception.InfectedNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ConfigItemNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ConfigItemNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handler(ex: ConfigItemNotFoundException) = ex.message
}