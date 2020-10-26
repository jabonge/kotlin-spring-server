package com.daly.server.common.util

import com.daly.server.domains.user.domain.User
import org.springframework.security.core.context.SecurityContextHolder

fun getCustomUserDetails(): User? {
    val principal = SecurityContextHolder.getContext().authentication?.principal
    return principal as? User
}