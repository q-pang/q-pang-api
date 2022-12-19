package com.qpang.deliveryservice.application.service

import com.qpang.deliveryservice.application.port.`in`.CompleteDeliveryUseCase
import com.qpang.deliveryservice.application.port.out.persistence.DeliveryPersistencePort
import com.qpang.deliveryservice.application.service.exception.DeliveryIdNotFoundException
import com.qpang.deliveryservice.domain.Delivery
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class CompleteDeliveryServiceDescribeSpec : DescribeSpec({
    val mockDeliveryPersistencePort: DeliveryPersistencePort = mockk()
    val completeDeliveryService = CompleteDeliveryService(mockDeliveryPersistencePort)

    describe("completeDelivery") {
        context("등록된 deliveryId를 가진 command가 주어지면") {
            val expectedDelivery = Delivery("orderId")
            every { mockDeliveryPersistencePort.findById(registeredDeliveryIdCommand.id) } answers { expectedDelivery }
            it("배송 완료에 성공하고 CompleteDeliveryInfo의 status가 COMPLETED 상태임") {
                val resultInfo = completeDeliveryService.command(registeredDeliveryIdCommand)

                resultInfo.status shouldBe Delivery.DeliveryStatus.COMPLETED
            }
        }

        context("등록되지 않은 productId를 가진 Command가 주어지면") {
            every { mockDeliveryPersistencePort.findById(notRegisteredDeliveryIdCommand.id) } answers { null }
            it("배송 완료에 실패하고 DeliveryIdNotFoundException 발생") {
                shouldThrow<DeliveryIdNotFoundException> {
                    completeDeliveryService.command(notRegisteredDeliveryIdCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val registeredDeliveryIdCommand = CompleteDeliveryUseCase.CompleteDeliveryCommand(
            id = "registeredId"
        )
        private val notRegisteredDeliveryIdCommand = CompleteDeliveryUseCase.CompleteDeliveryCommand(
            id = "notRegisteredId"
        )
    }
}