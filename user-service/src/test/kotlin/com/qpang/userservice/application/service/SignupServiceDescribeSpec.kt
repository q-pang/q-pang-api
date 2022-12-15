package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.SignupUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class SignupServiceDescribeSpec : DescribeSpec({
    val mockUserPersistencePort: UserPersistencePort = mockk()
    val signupService = SignupService(mockUserPersistencePort)

    describe("signup") {
        context("올바른 Command가 주어지면") {
            it("회원가입에 성공하고 SignupInfo 응답") {
                every { mockUserPersistencePort.save(any()) } answers { correctAnySignupCommand.toEntity() }
                val signupInfo = signupService.signup(correctAnySignupCommand)

                signupInfo.name shouldBe correctAnySignupCommand.name
            }
        }
    }
}) {
    companion object {
        private val correctAnySignupCommand = SignupUseCase.SignupCommand(
            name = "name"
        )
    }
}