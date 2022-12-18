package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.UpdateUserInfoUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import com.qpang.userservice.domain.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class UpdateUserInfoServiceDescribeSpec : DescribeSpec({
    val mockUserPersistencePort: UserPersistencePort = mockk()
    val updateUserInfoService = UpdateUserInfoService(mockUserPersistencePort)

    describe("updateUserInfo") {
        context("회원가입된 username을 가진 Command가 주어지면") {
            val expectedUser = User(username = "username", password = "password", name = "name")
            every { mockUserPersistencePort.findByUsername(registeredUserCommand.username) } answers { expectedUser }
            it("회원정보 변경에 성공하고 UpdateUserInfo 응답") {
                val updateUserInfo = updateUserInfoService.command(registeredUserCommand)

                updateUserInfo.name shouldBe registeredUserCommand.name
            }
        }

        context("회원가입되지 않은 username을 가진 Command가 주어지면") {
            every { mockUserPersistencePort.findByUsername(notRegisteredUserCommand.username) } answers { null }
            it("UsernameNotFoundException 발생") {
                shouldThrow<UsernameNotFoundException> {
                    updateUserInfoService.command(notRegisteredUserCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val registeredUserCommand = UpdateUserInfoUseCase.UpdateUserCommand(
            username = "username",
            name = "updatedName"
        )

        private val notRegisteredUserCommand = UpdateUserInfoUseCase.UpdateUserCommand(
            username = "username",
            name = "updatedName"
        )
    }
}