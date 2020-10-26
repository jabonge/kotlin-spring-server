package com.daly.server.domains.user.domain

import com.daly.server.domains.user.service.dto.UserDto
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class User(
        @Id
        @GeneratedValue
        var id: Long = 0,
        var email: String,
        var refreshToken: String? = null,
        @CreatedDate
        var createdAt: LocalDateTime = LocalDateTime.now(),
        @LastModifiedDate
        var updatedAt: LocalDateTime = LocalDateTime.now(),
        @JsonIgnore
        private var password: String,
        private var username: String
) : UserDetails {

    override fun getPassword(): String {
        return password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableSetOf()
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return username
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }


    fun toUserDto(): UserDto {
        return UserDto(id,email,refreshToken,createdAt.toString(),updatedAt.toString(),username)
    }


}


