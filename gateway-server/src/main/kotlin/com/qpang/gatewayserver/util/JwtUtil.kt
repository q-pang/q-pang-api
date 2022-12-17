package com.qpang.gatewayserver.util

import com.qpang.gatewayserver.exception.JwtValidateException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil(
    @Value("\${jwt.secret}")
    secret: String
) {
    private val secret = secret

    fun getToken(authHeader: String): String = authHeader.substring(7)

    fun resolveToken(token: String): Claims {
        try {
            return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            throw JwtValidateException(token)
        }
    }
}