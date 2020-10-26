package com.daly.server.domains.user.controller

import com.daly.server.common.response.CommonResponse
import com.daly.server.domains.user.service.UserService
import com.daly.server.domains.user.service.dto.SignInRequest
import com.daly.server.domains.user.service.dto.SignUpRequest
import com.daly.server.domains.user.service.dto.UserDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/auth"])
class UserController(private val userService: UserService) {

    @PostMapping(value = ["/signUp"])
    fun  signUp(@RequestBody signUpRequest: SignUpRequest): CommonResponse<Long> {
        userService.signUp(signUpRequest)
        return CommonResponse(HttpStatus.CREATED.value(),"User Created")
    }

    @PostMapping(value = ["/signIn"])
    fun signIn(@RequestBody signInRequest: SignInRequest): CommonResponse<UserDto> {
        return CommonResponse(HttpStatus.OK.value(),data = userService.signIn(signInRequest))
    }

    @GetMapping("/me")
    fun getMe(): CommonResponse<UserDto>{
        return CommonResponse(HttpStatus.OK.value(),data = userService.getMe().toUserDto())
    }
}