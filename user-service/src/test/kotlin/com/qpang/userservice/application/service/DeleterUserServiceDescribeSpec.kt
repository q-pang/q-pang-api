package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.DeleteUserUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import com.qpang.userservice.domain.User
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class DeleterUserServiceDescribeSpec : DescribeSpec({
    val mockUserPersistencePort: UserPersistencePort = mockk()
    val deleteUserService = DeleteUserService(mockUserPersistencePort)

    describe("deleteUser") {
        context("회원가입된 username을 가진 Command가 주어지면") {
            val expectedUser = User(username = "username", password = "password", name = "name")
            every { mockUserPersistencePort.findByUsername(registeredUserCommand.username) } answers { expectedUser }
            every { mockUserPersistencePort.delete(expectedUser) } answers {}
            it("회원탈퇴에 성공하고 DeleteUserInfo 응답") {
                val deleteUserInfo = deleteUserService.command(registeredUserCommand)

                deleteUserInfo.username shouldBe registeredUserCommand.username
            }
        }

        context("회원가입되지 않은 username을 가진 Command가 주어지면") {
            every { mockUserPersistencePort.findByUsername(notRegisteredUserCommand.username) } answers { null }
            it("UsernameNotFoundException 발생") {
                shouldThrow<UsernameNotFoundException> {
                    deleteUserService.command(notRegisteredUserCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val registeredUserCommand = DeleteUserUseCase.DeleteUserCommand(
            username = "username"
        )

        private val notRegisteredUserCommand = DeleteUserUseCase.DeleteUserCommand(
            username = "username"
        )
    }
}