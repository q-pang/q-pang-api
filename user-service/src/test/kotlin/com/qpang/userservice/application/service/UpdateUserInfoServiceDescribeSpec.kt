package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.UpdateUserInfoUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.UserIdNotFoundException
import com.qpang.userservice.domain.User
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class UpdateUserInfoServiceDescribeSpec : DescribeSpec({
    val mockUserPersistencePort: UserPersistencePort = mockk()
    val updateUserInfoService = UpdateUserInfoService(mockUserPersistencePort)

    describe("updateUserInfo") {
        context("회원가입된 user id를 가진 Command가 주어지면") {
            every { mockUserPersistencePort.findById(anyUpdateUserInfoCommand.id) } answers {
                User(
                    username = "username",
                    password = "password",
                    name = "name"
                )
            }
            it("회원정보 변경에 성공하고 UpdateUserInfo 응답") {
                val updateUserInfo = updateUserInfoService.command(anyUpdateUserInfoCommand)

                assertSoftly {
                    updateUserInfo.name shouldBe anyUpdateUserInfoCommand.name
                }
            }
        }

        context("회원가입되지 않은 user id를 가진 Command가 주어지면") {
            every { mockUserPersistencePort.findById(anyUpdateUserInfoCommand.id) } answers { null }
            it("UserIdNotFoundException 발생") {
                shouldThrow<UserIdNotFoundException> {
                    updateUserInfoService.command(anyUpdateUserInfoCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val anyUpdateUserInfoCommand = UpdateUserInfoUseCase.UpdateUserCommand(
            id = "018521bd-8c40-5af0-1465-ea7700d3e2b2",
            name = "updatedName"
        )
    }
}