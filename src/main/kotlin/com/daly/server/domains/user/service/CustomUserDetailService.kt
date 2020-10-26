package com.daly.server.domains.user.service

import com.daly.server.common.exception.CustomException
import com.daly.server.domains.user.domain.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(private val userRepository: UserRepository): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        username ?: throw CustomException("UserName Invalid",HttpStatus.BAD_REQUEST)
        return userRepository.findByUsername(username).orElseThrow {
            throw CustomException("UserName Not Found",HttpStatus.NOT_FOUND)
        }
    }

    fun loadUserByUserId(userId:Long): UserDetails {
        return userRepository.findById(userId).orElseThrow {
            throw CustomException("UserId Not Found",HttpStatus.NOT_FOUND)
        }
    }
}