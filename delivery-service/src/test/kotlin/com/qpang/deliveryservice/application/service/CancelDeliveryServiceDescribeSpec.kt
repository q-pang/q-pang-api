package com.qpang.deliveryservice.application.service

import com.qpang.deliveryservice.application.port.`in`.CancelDeliveryUseCase
import com.qpang.deliveryservice.application.port.out.persistence.DeliveryPersistencePort
import com.qpang.deliveryservice.application.service.exception.DeliveryIdNotFoundException
import com.qpang.deliveryservice.domain.Delivery
import com.qpang.deliveryservice.domain.exception.UncancellableException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class CancelDeliveryServiceDescribeSpec : DescribeSpec({
    val mockDeliveryPersistencePort: DeliveryPersistencePort = mockk()
    val cancelDeliveryService = CancelDeliveryService(mockDeliveryPersistencePort)

    describe("cancelDelivery") {
        context("등록된 deliveryId를 가진 Command가 주어지고") {
            context("delivery entity의 status가 AWAITING이면") {
                val expectedDelivery = Delivery("orderId")
                every { mockDeliveryPersistencePort.findById(registeredDeliveryIdCommand.id) } answers { expectedDelivery }
                it("배송 취소에 성공하고 CancelDeliveryInfo의 status가 CANCELLED 상태임") {
                    val resultInfo = cancelDeliveryService.command(registeredDeliveryIdCommand)

                    resultInfo.status shouldBe Delivery.DeliveryStatus.CANCELLED
                }
            }
            context("delivery entity의 status가 AWAITING이 아니면") {
                val expectedDelivery = Delivery("orderId")
                expectedDelivery.beginDelivery()
                every { mockDeliveryPersistencePort.findById(registeredDeliveryIdCommand.id) } answers { expectedDelivery }
                it("배송 취소에 실패하고 UncancellableException 발생") {
                    shouldThrow<UncancellableException> {
                        cancelDeliveryService.command(registeredDeliveryIdCommand)
                    }
                }
            }
        }

        context("등록되지 않은 productId를 가진 Command가 주어지면") {
            every { mockDeliveryPersistencePort.findById(notRegisteredDeliveryIdCommand.id) } answers { null }
            it("배송 완료에 실패하고 DeliveryIdNotFoundException 발생") {
                shouldThrow<DeliveryIdNotFoundException> {
                    cancelDeliveryService.command(notRegisteredDeliveryIdCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val registeredDeliveryIdCommand = CancelDeliveryUseCase.CancelDeliveryCommand(
            id = "registeredId"
        )
        private val notRegisteredDeliveryIdCommand = CancelDeliveryUseCase.CancelDeliveryCommand(
            id = "notRegisteredId"
        )
    }
}