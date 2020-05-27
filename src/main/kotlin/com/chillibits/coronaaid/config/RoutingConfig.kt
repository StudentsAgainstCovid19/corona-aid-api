package com.chillibits.coronaaid.config

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import springfox.documentation.annotations.ApiIgnore

@Controller
@ApiIgnore
class RoutingConfig {
    @RequestMapping(method = [RequestMethod.GET], value = ["/"])
    @ApiOperation(value = "Redirects the user to the swagger ui page", hidden = true)
    fun swagger() = "redirect:/swagger-ui5.html"
}