package com.qpang.userservice.application.port.out.persistence

import com.qpang.userservice.domain.User

interface UserPersistencePort {
    fun save(user: User): User
    fun existsByUsername(username: String): Boolean
    fun findById(id: String): User?
    fun findByUsername(username: String): User?
}