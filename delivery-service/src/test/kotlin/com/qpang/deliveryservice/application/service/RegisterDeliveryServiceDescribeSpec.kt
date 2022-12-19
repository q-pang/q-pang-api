package com.qpang.deliveryservice.application.service

import com.qpang.deliveryservice.application.port.`in`.RegisterDeliveryUseCase
import com.qpang.deliveryservice.application.port.out.persistence.DeliveryPersistencePort
import com.qpang.deliveryservice.domain.Delivery
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class RegisterDeliveryServiceDescribeSpec : DescribeSpec({
    val mockDeliveryPersistencePort: DeliveryPersistencePort = mockk()
    val registerDeliveryService = RegisterDeliveryService(mockDeliveryPersistencePort)

    describe("registerDelivery") {
        context("command가 주어지면") {
            val expectedDelivery = Delivery(command.userId, command.orderId)
            every { mockDeliveryPersistencePort.save(any()) } answers { expectedDelivery }
            it("배송 등록에 성공하고 RegisterDeliveryInfo 응답") {
                val resultInfo = registerDeliveryService.command(command)

                assertSoftly {
                    resultInfo.userId shouldBe command.userId
                    resultInfo.orderId shouldBe command.orderId
                }
            }
        }
    }
}) {
    companion object {
        private val command = RegisterDeliveryUseCase.RegisterDeliveryCommand(
            userId = "userId",
            orderId = "orderId"
        )
    }
}