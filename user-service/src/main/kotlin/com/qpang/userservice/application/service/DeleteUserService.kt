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
        val user = userPersistencePort.findByUsername(command.username)
        user ?: throw UsernameNotFoundException(command.username)
        userPersistencePort.delete(user)

        return DeleteUserUseCase.DeleteUserInfo(
            username = user.username,
            name = user.name
        )
    }
}