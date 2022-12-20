package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.GetUserUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.UserIdNotFoundException
import com.qpang.userservice.domain.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk

class GetUserServiceDescribeSpec : DescribeSpec({
    val mockUserPersistencePort: UserPersistencePort = mockk()
    val getUserService = GetUserService(mockUserPersistencePort)

    describe("getUser") {
        context("회원가입된 userId를 가진 Command가 주어지면") {
            val expectedUser = User(username = "username", password = "password", name = "name")
            every { mockUserPersistencePort.findUserById(registeredUserCommand.id) } answers { expectedUser }
            it("유저 조회에 성공하고 GetUserInfo 응답") {
                val resultInfo = getUserService.command(registeredUserCommand)
            }
        }

        context("회원가입되지 않은 userId를 가진 Command가 주어지면") {
            every { mockUserPersistencePort.findUserById(notRegisteredUserCommand.id) } answers { null }
            it("UserIdNotFoundException 발생") {
                shouldThrow<UserIdNotFoundException> {
                    getUserService.command(notRegisteredUserCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val registeredUserCommand = GetUserUseCase.GetUserCommand(
            id = "registeredId"
        )

        private val notRegisteredUserCommand = GetUserUseCase.GetUserCommand(
            id = "notRegisteredId"
        )
    }
}