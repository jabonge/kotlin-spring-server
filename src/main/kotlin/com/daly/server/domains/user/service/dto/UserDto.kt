package com.daly.server.domains.user.service.dto

import com.fasterxml.jackson.annotation.JsonInclude

data class UserDto(val id:Long,
                   val email:String,
                   val refreshToken:String?,
                   val createdAt:String,
                   val updatedAt:String,
                   val username:String,
                   @JsonInclude(JsonInclude.Include.NON_NULL)
                   var accessToken:String? = null)
