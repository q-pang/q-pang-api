package com.qpang.userservice.adapter.out.persistence

import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.domain.User
import com.qpang.userservice.infrastructure.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val userRepository: UserRepository
) : UserPersistencePort {
    override fun save(user: User): User = userRepository.save(user)
    override fun existsByUsername(username: String): Boolean = userRepository.existsByUsername(username)
    override fun findByUsername(username: String): User? = userRepository.findByUsername(username)
}