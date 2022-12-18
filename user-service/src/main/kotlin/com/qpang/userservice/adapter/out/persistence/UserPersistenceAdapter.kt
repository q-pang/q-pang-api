package com.qpang.userservice.adapter.out.persistence

import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.domain.User
import com.qpang.userservice.infrastructure.repository.UserRepository
import com.qpang.userservice.infrastructure.repository.UserRepositorySupport
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val userRepository: UserRepository,
    private val userRepositorySupport: UserRepositorySupport
) : UserPersistencePort {
    override fun save(user: User): User = userRepository.save(user)
    override fun existsByUsername(username: String): Boolean = userRepository.existsByUsername(username)
    override fun findByUsername(username: String): User? = userRepositorySupport.findByUsername(username)
    override fun delete(user: User) = userRepository.delete(user)
}