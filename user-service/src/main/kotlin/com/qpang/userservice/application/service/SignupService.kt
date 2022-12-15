package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.SignupUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.DuplicateUsernameException
import org.springframework.stereotype.Service

@Service
class SignupService(
    private val userPersistencePort: UserPersistencePort
) : SignupUseCase {
    override fun signup(command: SignupUseCase.SignupCommand): SignupUseCase.SignupInfo {
        if (userPersistencePort.existsByUsername(command.username)) {
            throw DuplicateUsernameException(command.username)
        }

        return SignupUseCase.SignupInfo.from(userPersistencePort.save(command.toEntity()))
    }
}