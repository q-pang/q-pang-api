package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.RegisterPaymentMethodUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import com.qpang.userservice.domain.PaymentMethod
import com.qpang.userservice.domain.User
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class RegisterPaymentMethodServiceDescribeSpec : DescribeSpec({
    val mockUserPersistencePort: UserPersistencePort = mockk()
    val registerPaymentMethodService = RegisterPaymentMethodService(mockUserPersistencePort)

    describe("registerPaymentMethod") {
        context("회원가입된 username을 가진 Command가 주어지면") {
            val expectedUser = User(username = "username", password = "password", name = "name")
            every { mockUserPersistencePort.findByUsername(registeredUserCommand.username) } answers { expectedUser }
            it("결제수단 추가에 성공하고 RegisterPaymentMethodInfo 응답") {
                val registerPaymentMethodInfo = registerPaymentMethodService.command(registeredUserCommand)

                assertSoftly {
                    registerPaymentMethodInfo.username shouldBe registeredUserCommand.username
                    registerPaymentMethodInfo.type shouldBe registeredUserCommand.type
                    registerPaymentMethodInfo.company shouldBe registeredUserCommand.company
                    registerPaymentMethodInfo.number shouldBe registeredUserCommand.number
                }
            }
        }

        context("회원가입되지 않은 username을 가진 Command가 주어지면") {
            every { mockUserPersistencePort.findByUsername(notRegisteredUserCommand.username) } answers { null }
            it("UsernameNotFoundException 발생") {
                shouldThrow<UsernameNotFoundException> {
                    registerPaymentMethodService.command(notRegisteredUserCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val registeredUserCommand = RegisterPaymentMethodUseCase.RegisterPaymentMethodCommand(
            username = "username",
            type = PaymentMethod.PaymentMethodType.CREDITCARD,
            company = PaymentMethod.CardCompany.SAMSUNG,
            number = "1234567890"
        )

        private val notRegisteredUserCommand = RegisterPaymentMethodUseCase.RegisterPaymentMethodCommand(
            username = "username",
            type = PaymentMethod.PaymentMethodType.CREDITCARD,
            company = PaymentMethod.CardCompany.SAMSUNG,
            number = "1234567890"
        )
    }
}