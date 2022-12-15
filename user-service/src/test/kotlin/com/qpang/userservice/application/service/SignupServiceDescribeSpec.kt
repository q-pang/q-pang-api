package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.SignupUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.DuplicateUsernameException
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class SignupServiceDescribeSpec : DescribeSpec({
    val mockUserPersistencePort: UserPersistencePort = mockk()
    val passwordEncoder = BCryptPasswordEncoder()
    val signupService = SignupService(mockUserPersistencePort, passwordEncoder)

    describe("signup") {
        every { mockUserPersistencePort.save(any()) } answers { anySignupCommand.toEntity() }

        context("회원가입되지 않은 username을 가진 Command가 주어지면") {
            every { mockUserPersistencePort.existsByUsername(anySignupCommand.username) } answers { false }
            it("회원가입에 성공하고 SignupInfo 응답") {
                val signupInfo = signupService.signup(anySignupCommand)

                assertSoftly {
                    signupInfo.username shouldBe anySignupCommand.username
                    signupInfo.name shouldBe anySignupCommand.name
                }
            }
        }

        context("이미 회원가입된 username을 가진 Command가 주어지면") {
            every { mockUserPersistencePort.existsByUsername(anySignupCommand.username) } answers { true }
            it("DuplicateUsernameException 발생") {
                shouldThrow<DuplicateUsernameException> {
                    signupService.signup(anySignupCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val anySignupCommand = SignupUseCase.SignupCommand(
            username = "username",
            password = "password",
            name = "name"
        )
    }
}