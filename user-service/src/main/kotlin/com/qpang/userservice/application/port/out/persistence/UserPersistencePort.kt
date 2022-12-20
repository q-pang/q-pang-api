package com.qpang.userservice.application.port.out.persistence

import com.qpang.userservice.domain.User

interface UserPersistencePort {
    fun saveUser(user: User): User
    fun existsUserByUsername(username: String): Boolean
    fun findUserByUsername(username: String): User?
    fun findUserById(id: String): User?
    fun deleteUser(user: User)
}