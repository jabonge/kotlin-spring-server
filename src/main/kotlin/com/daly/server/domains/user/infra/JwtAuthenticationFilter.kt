package com.daly.server.domains.user.infra

import com.daly.server.common.exception.CustomException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(private val jwtTokenProvider: JwtTokenProvider) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = jwtTokenProvider.getToken(request)
        token?.let {
            try{
                val authentication = jwtTokenProvider.getAuthentication(it)
                SecurityContextHolder.getContext().authentication = authentication
            } catch (e: CustomException) {
                response.writer.write(ObjectMapper().writeValueAsString(e.toCustomError()))
            }


        }
        filterChain.doFilter(request, response)
    }
}