package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.GetUserUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetUserService(
    private val userPersistencePort: UserPersistencePort
) : GetUserUseCase {
    @Transactional(readOnly = true)
    override fun command(command: GetUserUseCase.GetUserCommand): GetUserUseCase.GetUserInfo {
        val savedUser = userPersistencePort.findUserById(command.id)
        savedUser ?: throw UsernameNotFoundException(command.id)

        return GetUserUseCase.GetUserInfo(
            id = savedUser.getId(),
            username = savedUser.username,
            name = savedUser.name,
        )
    }
}