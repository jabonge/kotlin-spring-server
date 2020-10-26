package com.daly.server.common.exception


import org.springframework.http.HttpStatus
import java.lang.RuntimeException

class CustomException(override var message: String = "",val status:HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,val code:Int = status.value()):RuntimeException(){

    fun toCustomError() = CustomError(status,code,message)
}