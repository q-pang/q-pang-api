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
        context("회원가입되지 않은 username을 가진 Command가 주어지면") {
            every { mockUserPersistencePort.saveUser(any()) } answers { notDuplicatedUsernameCommand.toEntity() }
            every { mockUserPersistencePort.existsUserByUsername(notDuplicatedUsernameCommand.username) } answers { false }
            it("회원가입에 성공하고 SignupInfo 응답") {
                val resultInfo = signupService.command(notDuplicatedUsernameCommand)

                assertSoftly {
                    resultInfo.username shouldBe notDuplicatedUsernameCommand.username
                    resultInfo.name shouldBe notDuplicatedUsernameCommand.name
                }
            }
        }

        context("이미 회원가입된 username을 가진 Command가 주어지면") {
            every { mockUserPersistencePort.existsUserByUsername(duplicatedUsernameCommand.username) } answers { true }
            it("DuplicateUsernameException 발생") {
                shouldThrow<DuplicateUsernameException> {
                    signupService.command(duplicatedUsernameCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val duplicatedUsernameCommand = SignupUseCase.SignupCommand(
            username = "username",
            password = "password",
            name = "name"
        )

        private val notDuplicatedUsernameCommand = SignupUseCase.SignupCommand(
            username = "username",
            password = "password",
            name = "name"
        )
    }
}