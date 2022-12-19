package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.DeleteUserUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteUserService(
    private val userPersistencePort: UserPersistencePort
) : DeleteUserUseCase {
    @Transactional
    override fun command(command: DeleteUserUseCase.DeleteUserCommand): DeleteUserUseCase.DeleteUserInfo {
        val savedUser = userPersistencePort.findUserByUsername(command.username)
        savedUser ?: throw UsernameNotFoundException(command.username)
        userPersistencePort.deleteUser(savedUser)

        return DeleteUserUseCase.DeleteUserInfo(
            username = savedUser.username,
            name = savedUser.name
        )
    }
}