package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.DeletePaymentMethodUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.PaymentMethodNotFoundException
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import com.qpang.userservice.domain.PaymentMethod
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
        context("회원가입된 username을 가지고") {
            context("등록된 paymentMethodId를 가진 Command가 주어지면") {
                val expectedUser = User(username = "username", password = "password", name = "name")
                val registeredPaymentMethod = PaymentMethod(
                    type = PaymentMethod.PaymentMethodType.CREDITCARD,
                    company = PaymentMethod.CardCompany.SAMSUNG,
                    number = "1234567890",
                    user = expectedUser
                )
                expectedUser.registerPaymentMethod(registeredPaymentMethod)
                val registeredPaymentMethodCommand = DeletePaymentMethodUseCase.DeletePaymentMethodCommand(
                    username = "username",
                    paymentMethodId = registeredPaymentMethod.getId()
                )

                every { mockUserPersistencePort.findByUsername(registeredPaymentMethodCommand.username) } answers { expectedUser }
                it("결제수단 삭제에 성공하고 DeletePaymentMethodInfo 응답") {
                    val deletePaymentMethodInfo = deletePaymentMethodService.command(registeredPaymentMethodCommand)

                    deletePaymentMethodInfo.username shouldBe registeredPaymentMethodCommand.username
                }
            }

            context("등록되지 않은 paymentMethodId를 가진 Command가 주어지면") {
                val expectedUser = User(username = "username", password = "password", name = "name")
                every { mockUserPersistencePort.findByUsername(notRegisteredPaymentMethodCommand.username) } answers { expectedUser }
                it("결제수단 삭제에 실패하고 PaymentMethodNotFoundException 발생") {
                    shouldThrow<PaymentMethodNotFoundException> {
                        deletePaymentMethodService.command(notRegisteredPaymentMethodCommand)
                    }
                }
            }
        }

        context("회원가입되지 않은 username을 가진 Command가 주어지면") {
            every { mockUserPersistencePort.findByUsername(notRegisteredUserCommand.username) } answers { null }
            it("UsernameNotFoundException 발생") {
                shouldThrow<UsernameNotFoundException> {
                    deletePaymentMethodService.command(notRegisteredUserCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val notRegisteredUserCommand = DeletePaymentMethodUseCase.DeletePaymentMethodCommand(
            username = "username",
            paymentMethodId = "paymentMethodId"
        )

        private val notRegisteredPaymentMethodCommand = DeletePaymentMethodUseCase.DeletePaymentMethodCommand(
            username = "username",
            paymentMethodId = "paymentMethodId"
        )
    }
}