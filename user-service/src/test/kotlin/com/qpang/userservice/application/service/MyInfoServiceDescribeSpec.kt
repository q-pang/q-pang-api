package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.MyInfoUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import com.qpang.userservice.domain.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class MyInfoServiceDescribeSpec : DescribeSpec({
    val mockUserPersistencePort: UserPersistencePort = mockk()
    val myInfoService = MyInfoService(mockUserPersistencePort)

    describe("myInfo") {
        context("회원가입된 username을 가진 Command가 주어지면") {
            val expectedUser = User(username = "username", password = "password", name = "name")
            every { mockUserPersistencePort.findUserByUsername(registeredUserCommand.username) } answers { expectedUser }
            it("내 정보 조회에 성공하고 GetUserInfo 응답") {
                val getUserInfo = myInfoService.command(registeredUserCommand)

                getUserInfo.username shouldBe registeredUserCommand.username
            }
        }

        context("회원가입되지 않은 username을 가진 Command가 주어지면") {
            every { mockUserPersistencePort.findUserByUsername(notRegisteredUserCommand.username) } answers { null }
            it("UsernameNotFoundException 발생") {
                shouldThrow<UsernameNotFoundException> {
                    myInfoService.command(notRegisteredUserCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val registeredUserCommand = MyInfoUseCase.MyInfoCommand(
            username = "username"
        )

        private val notRegisteredUserCommand = MyInfoUseCase.MyInfoCommand(
            username = "username"
        )
    }
}