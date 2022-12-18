package com.qpang.userservice.infrastructure.repository

import com.qpang.userservice.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): User?
}