package com.daly.server.common.exception

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import javax.servlet.http.HttpServletRequest


@Controller
@RequestMapping("/error")
class CustomErrorController(val errorAttributes: ErrorAttributes): AbstractErrorController(errorAttributes) {

    override fun getErrorPath(): String {
        return "/error"
    }

    @RequestMapping(method = [RequestMethod.GET, RequestMethod.POST])
    fun error(request: HttpServletRequest?): ResponseEntity<CustomError> {
        val message = getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE))["message"].toString()
        val status = getStatus(request)
        return ResponseEntity(CustomError(status,message = message), status)
    }
}