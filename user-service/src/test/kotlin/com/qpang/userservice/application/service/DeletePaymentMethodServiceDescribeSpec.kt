package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.DeletePaymentMethodUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import com.qpang.userservice.domain.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class DeletePaymentMethodServiceDescribeSpec : DescribeSpec({
    val mockUserPersistencePort: UserPersistencePort = mockk()
    val deletePaymentMethodService = DeletePaymentMethodService(mockUserPersistencePort)

    describe("deletePaymentMethod") {
        context("회원가입된 username을 가진 Command가 주어지면") {
            val expectedUser = User(username = "username", password = "password", name = "name")
            every { mockUserPersistencePort.findByUsername(anyDeletePaymentMethodCommand.username) } answers { expectedUser }
            it("결제수단 삭제에 성공하고 DeletePaymentMethodInfo 응답") {
                val deletePaymentMethodInfo = deletePaymentMethodService.command(anyDeletePaymentMethodCommand)

                deletePaymentMethodInfo.username shouldBe anyDeletePaymentMethodCommand.username
            }
        }

        context("회원가입되지 않은 username을 가진 Command가 주어지면") {
            every { mockUserPersistencePort.findByUsername(anyDeletePaymentMethodCommand.username) } answers { null }
            it("UsernameNotFoundException 발생") {
                shouldThrow<UsernameNotFoundException> {
                    deletePaymentMethodService.command(anyDeletePaymentMethodCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val anyDeletePaymentMethodCommand = DeletePaymentMethodUseCase.DeletePaymentMethodCommand(
            username = "username",
            paymentMethodId = "paymentMethodId"
        )
    }
}