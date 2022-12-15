package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.port.`in`.usecase.SignupUseCase
import org.springframework.stereotype.Service

@Service
class SignupService(
    private val userPersistencePort: UserPersistencePort
) : SignupUseCase {
    override fun signup(command: SignupUseCase.SignupCommand) {
        userPersistencePort.save(command.toEntity())
    }
}