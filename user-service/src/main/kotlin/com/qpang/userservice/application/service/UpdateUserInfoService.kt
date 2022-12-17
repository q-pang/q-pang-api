package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.UpdateUserInfoUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.UserIdNotFoundException
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateUserInfoService(
    private val userPersistencePort: UserPersistencePort
) : UpdateUserInfoUseCase {
    @Transactional
    override fun command(command: UpdateUserInfoUseCase.UpdateUserCommand): UpdateUserInfoUseCase.UpdateUserInfo {
        val user = userPersistencePort.findByUsername(command.username)
        user ?: throw UsernameNotFoundException(command.username)
        user.updateInfo(command)

        return UpdateUserInfoUseCase.UpdateUserInfo(
            id = user.getId(),
            username = user.username,
            name = user.name
        )
    }
}