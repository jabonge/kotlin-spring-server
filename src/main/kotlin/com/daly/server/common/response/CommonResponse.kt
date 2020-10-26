package com.daly.server.common.response

import com.fasterxml.jackson.annotation.JsonInclude

data class CommonResponse<T>(val status:Int,val message:String="",@JsonInclude(JsonInclude.Include.NON_NULL) val data:T? = null)