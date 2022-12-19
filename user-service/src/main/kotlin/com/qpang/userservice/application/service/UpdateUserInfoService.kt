package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.UpdateUserInfoUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateUserInfoService(
    private val userPersistencePort: UserPersistencePort
) : UpdateUserInfoUseCase {
    @Transactional
    override fun command(command: UpdateUserInfoUseCase.UpdateUserCommand): UpdateUserInfoUseCase.UpdateUserInfo {
        val savedUser = userPersistencePort.findUserByUsername(command.username)
        savedUser ?: throw UsernameNotFoundException(command.username)
        savedUser.updateInfo(command)

        return UpdateUserInfoUseCase.UpdateUserInfo(
            id = savedUser.getId(),
            username = savedUser.username,
            name = savedUser.name
        )
    }
}