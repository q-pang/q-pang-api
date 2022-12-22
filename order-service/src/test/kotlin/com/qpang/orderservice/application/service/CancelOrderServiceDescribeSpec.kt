package com.qpang.orderservice.application.service

import com.qpang.orderservice.application.port.`in`.CancelOrderUseCase
import com.qpang.orderservice.application.port.out.event.EventProducePort
import com.qpang.orderservice.application.port.out.external.PaymentPort
import com.qpang.orderservice.application.port.out.persistence.OrderPersistencePort
import com.qpang.orderservice.application.port.out.rest.DeliveryServiceRestPort
import com.qpang.orderservice.application.service.exception.OrderNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk

class CancelOrderServiceDescribeSpec : DescribeSpec({
    val mockOrderPersistencePort: OrderPersistencePort = mockk()
    val mockPaymentPort: PaymentPort = mockk()
    val mockEventProducePort: EventProducePort = mockk()
    val mockDeliveryServiceRestPort: DeliveryServiceRestPort = mockk()
    val cancelOrderService =
        CancelOrderService(mockOrderPersistencePort, mockPaymentPort, mockEventProducePort, mockDeliveryServiceRestPort)

    describe("cancelOrder") {
        context("등록되지 않은 orderId를 가진 커맨드가 주어지면") {
            every { mockOrderPersistencePort.findOrderById("notRegisteredOrderId") } answers { null }
            it("주문 취소에 실패하고 OrderNotFoundException 발생") {
                shouldThrow<OrderNotFoundException> {
                    cancelOrderService.command(notRegisteredOrderIdCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val notRegisteredOrderIdCommand: CancelOrderUseCase.CancelOrderCommand =
            CancelOrderUseCase.CancelOrderCommand("notRegisteredOrderId")
    }
}