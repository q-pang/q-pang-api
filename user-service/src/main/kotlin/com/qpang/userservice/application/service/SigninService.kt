package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.SigninUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.IncorrectPasswordException
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import com.qpang.userservice.infrastructure.token.JwtProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SigninService(
    private val userPersistencePort: UserPersistencePort,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtProvider: JwtProvider
) : SigninUseCase {
    @Transactional(readOnly = true)
    override fun command(command: SigninUseCase.SigninCommand): SigninUseCase.SigninInfo {
        val savedUser = userPersistencePort.findUserByUsername(command.username)
        savedUser ?: throw UsernameNotFoundException(command.username)
        if (!savedUser.isCorrectPassword(passwordEncoder, command.password)) throw IncorrectPasswordException(command.username)
        val token = jwtProvider.generateToken(command.username)

        return SigninUseCase.SigninInfo(
            username = savedUser.username,
            token = token
        )
    }
}