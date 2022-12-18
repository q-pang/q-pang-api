package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.SigninUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import com.qpang.userservice.domain.User
import com.qpang.userservice.infrastructure.token.JwtProvider
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class SigninServiceDescribeSpec : DescribeSpec({
    val mockUserPersistencePort: UserPersistencePort = mockk()
    val passwordEncoder = BCryptPasswordEncoder()
    val jwtProvider = JwtProvider("secret", 36000000)
    val signinService = SigninService(mockUserPersistencePort, passwordEncoder, jwtProvider)

    describe("signin") {
        context("회원가입된 username과 올바른 password가 주어지면") {
            val expectedUser = User(
                username = registeredUserCommand.username,
                password = passwordEncoder.encode(registeredUserCommand.password),
                name = "name"
            )
            every { mockUserPersistencePort.findByUsername(registeredUserCommand.username) } answers { expectedUser }
            it("로그인에 성공하고 SigninInfo 응답") {
                val signinInfo = signinService.command(registeredUserCommand)

                signinInfo.username shouldBe registeredUserCommand.username
            }
        }

        context("회원가입되지 않은 username이 주어지면") {
            every { mockUserPersistencePort.findByUsername(notRegisteredUserCommand.username) } answers { null }
            it("UsernameNotFoundException 발생") {
                shouldThrow<UsernameNotFoundException> {
                    signinService.command(notRegisteredUserCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val registeredUserCommand = SigninUseCase.SigninCommand(
            username = "username",
            password = "password"
        )

        private val notRegisteredUserCommand = SigninUseCase.SigninCommand(
            username = "username",
            password = "password"
        )
    }
}