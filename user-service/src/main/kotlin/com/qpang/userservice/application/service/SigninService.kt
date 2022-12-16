package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.SigninUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.IncorrectPasswordException
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import com.qpang.userservice.infrastructure.token.JwtProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class SigninService(
    private val userPersistencePort: UserPersistencePort,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtProvider: JwtProvider
) : SigninUseCase {
    override fun command(command: SigninUseCase.SigninCommand): SigninUseCase.SigninInfo {
        val user = userPersistencePort.findByUsername(command.username)
        user ?: throw UsernameNotFoundException(command.username)
        if (!user.isCorrectPassword(passwordEncoder, command.password)) throw IncorrectPasswordException(command.username)
        val token = jwtProvider.generateToken(command.username)

        return SigninUseCase.SigninInfo(
            username = user.username,
            token = token
        )
    }
}