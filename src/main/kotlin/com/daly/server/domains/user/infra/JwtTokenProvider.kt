package com.daly.server.domains.user.infra

import com.daly.server.common.exception.CustomException
import com.daly.server.domains.user.service.CustomUserDetailService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.lang.Exception
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(private val userDetailsService: CustomUserDetailService) {

    private final val signKey = "daly_server_access_key_it_need_above_256bit"
    private final val now = Date()
    private final val key = Keys.hmacShaKeyFor(signKey.toByteArray())


    fun createToken(id: Long): String {
        return Jwts.builder().setSubject(id.toString())
                .setIssuedAt(now)
                .setExpiration(Date(now.time + 2 * 1000 * 60 * 60))
                .signWith(key).compact()
    }

    fun getUserIdFromJwt(jwt: String): Long {
        try {
            val userId = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).body.subject
            return userId.toLong()
        } catch (e: JwtException) {
            when (e) {
                (e as? ExpiredJwtException) -> throw CustomException(status = HttpStatus.UNAUTHORIZED, message = e.localizedMessage, code = 701)
                else -> throw CustomException(status = HttpStatus.UNAUTHORIZED, message = e.localizedMessage)
            }
        }
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailsService.loadUserByUserId(getUserIdFromJwt(token))
        return UsernamePasswordAuthenticationToken(userDetails,null,userDetails.authorities)
    }

    fun getToken(req: HttpServletRequest): String? {
        return req.getHeader(HttpHeaders.AUTHORIZATION)?.substring(7)
    }

    fun verifyToken(token: String): Boolean {
        return try{
            val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            !claims.body.expiration.before(Date())
        } catch (exception: Exception) {
            false
        }
    }
}