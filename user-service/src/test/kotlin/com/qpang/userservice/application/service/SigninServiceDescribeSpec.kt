package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.SigninUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import com.qpang.userservice.domain.User
import com.qpang.userservice.infrastructure.token.JwtProvider
import io.kotest.assertions.assertSoftly
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
                username = anySigninCommand.username,
                password = passwordEncoder.encode(anySigninCommand.password),
                name = "name"
            )
            every { mockUserPersistencePort.findByUsername(anySigninCommand.username) } answers { expectedUser }
            it("로그인에 성공하고 SigninInfo 응답") {
                val signinInfo = signinService.command(anySigninCommand)

                assertSoftly {
                    signinInfo.username shouldBe anySigninCommand.username
                }
            }
        }

        context("회원가입되지 않은 username이 주어지면") {
            every { mockUserPersistencePort.findByUsername(anySigninCommand.username) } answers { null }
            it("UsernameNotFoundException 발생") {
                shouldThrow<UsernameNotFoundException> {
                    signinService.command(anySigninCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val anySigninCommand = SigninUseCase.SigninCommand(
            username = "username",
            password = "password"
        )
    }
}