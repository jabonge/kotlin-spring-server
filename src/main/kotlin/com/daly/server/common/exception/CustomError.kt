package com.daly.server.common.exception

import org.springframework.http.HttpStatus

data class CustomError(val status:HttpStatus,val code:Int = status.value(),val message:String? = "")
