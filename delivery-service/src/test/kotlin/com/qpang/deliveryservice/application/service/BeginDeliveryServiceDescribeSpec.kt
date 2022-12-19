package com.qpang.deliveryservice.application.service

import com.qpang.deliveryservice.application.port.`in`.BeginDeliveryUseCase
import com.qpang.deliveryservice.application.port.out.persistence.DeliveryPersistencePort
import com.qpang.deliveryservice.application.service.exception.DeliveryIdNotFoundException
import com.qpang.deliveryservice.domain.Delivery
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class BeginDeliveryServiceDescribeSpec : DescribeSpec({
    val mockDeliveryPersistencePort: DeliveryPersistencePort = mockk()
    val beginDeliveryService = BeginDeliveryService(mockDeliveryPersistencePort)

    describe("beginDelivery") {
        context("등록된 deliveryId를 가진 command가 주어지면") {
            val expectedDelivery = Delivery("orderId")
            every { mockDeliveryPersistencePort.findById(registeredDeliveryIdCommand.id) } answers { expectedDelivery }
            it("배송 시작에 성공하고 BeginDeliveryInfo의 status가 INPROGRESS 상태임") {
                val resultInfo = beginDeliveryService.command(registeredDeliveryIdCommand)

                resultInfo.status shouldBe Delivery.DeliveryStatus.INPROGRESS
            }
        }

        context("등록되지 않은 productId를 가진 Command가 주어지면") {
            every { mockDeliveryPersistencePort.findById(notRegisteredDeliveryIdCommand.id) } answers { null }
            it("배송 시작에 실패하고 DeliveryIdNotFoundException 발생") {
                shouldThrow<DeliveryIdNotFoundException> {
                    beginDeliveryService.command(notRegisteredDeliveryIdCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val registeredDeliveryIdCommand = BeginDeliveryUseCase.BeginDeliveryCommand(
            id = "registeredId"
        )
        private val notRegisteredDeliveryIdCommand = BeginDeliveryUseCase.BeginDeliveryCommand(
            id = "notRegisteredId"
        )
    }
}