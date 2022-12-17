package com.qpang.userservice.infrastructure.token

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtProvider(
    @Value("\${jwt.secret}")
    secret: String,
    @Value("\${jwt.validity-in-milliseconds}")
    validityInMilliseconds: Long
) {
    private val secret = secret
    private val validityInMilliseconds = validityInMilliseconds

    fun generateToken(username: String): String {
        val claims: Claims = Jwts.claims().setSubject(username)
        val validity = Date(Date().time + validityInMilliseconds)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

}