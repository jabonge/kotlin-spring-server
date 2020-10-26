package com.daly.server.domains.user.service

import com.daly.server.common.exception.CustomException
import com.daly.server.common.util.getCustomUserDetails
import com.daly.server.domains.user.domain.User
import com.daly.server.domains.user.domain.UserRepository
import com.daly.server.domains.user.infra.JwtTokenProvider
import com.daly.server.domains.user.service.dto.SignInRequest
import com.daly.server.domains.user.service.dto.SignUpRequest
import com.daly.server.domains.user.service.dto.UserDto
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.lang.Exception
import java.lang.IllegalArgumentException

@Service
class UserService(private val jwtTokenProvider: JwtTokenProvider, private val userRepository: UserRepository,private val passwordEncoder: PasswordEncoder) {

    fun signUp(signUpRequest: SignUpRequest): Boolean {
        if(!userRepository.findByEmail(email = signUpRequest.email).isEmpty){
            throw CustomException(status = HttpStatus.BAD_REQUEST, message = "Duplicate User Email")
        }
        val user = User(email = signUpRequest.email, password = passwordEncoder.encode(signUpRequest.password), username = signUpRequest.name)
        userRepository.save(user)
        return true
    }

    fun signIn(signInRequest: SignInRequest): UserDto {
        val user = userRepository.findByEmail(signInRequest.email).orElseThrow {
            throw CustomException(status = HttpStatus.BAD_REQUEST, message = "Email Not Found")
        }
        if(!passwordEncoder.matches(signInRequest.password,user.password)) {
            throw CustomException(status = HttpStatus.BAD_REQUEST, message = "Password Not Valid")
        }else {
            val userDto = user.toUserDto()
            userDto.accessToken = jwtTokenProvider.createToken(user.id)
            return userDto
        }
    }

    fun getMe(): User {
        val userId = getCustomUserDetails()?.id
        if(userId == null) {
            throw CustomException(status = HttpStatus.NOT_FOUND, message = "User Not Found")
        } else {
            return userRepository.findById(userId).orElseThrow {
                throw CustomException(status = HttpStatus.NOT_FOUND, message = "User Not Found")
            }
        }
    }
    
}