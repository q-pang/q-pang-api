package com.qpang.orderservice.application.service

import com.qpang.orderservice.adapter.out.external.exception.FailedCancelPaymentException
import com.qpang.orderservice.application.port.`in`.CancelOrderUseCase
import com.qpang.orderservice.application.port.out.event.EventProducePort
import com.qpang.orderservice.application.port.out.external.PaymentPort
import com.qpang.orderservice.application.port.out.persistence.OrderPersistencePort
import com.qpang.orderservice.application.port.out.rest.DeliveryServiceRestPort
import com.qpang.orderservice.application.port.out.rest.dto.DeliveryResponseDto
import com.qpang.orderservice.application.service.exception.OrderNotFoundException
import com.qpang.orderservice.application.service.exception.UncancellableException
import com.qpang.orderservice.domain.Order
import com.qpang.orderservice.domain.Payment
import com.qpang.orderservice.domain.exception.AlreadyCanceledFoundException
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
        context("등록된 orderId를 가지고") {

            context("주문이 이미 취소 상태이면") {
                val expectedOrder = Order("registeredConsumerId")
                every { mockOrderPersistencePort.findOrderById("registeredOrderId") } answers { expectedOrder }
                expectedOrder.cancel()
                it("주문 취소에 실패하고 AlreadyCanceledFoundException 발생") {
                    shouldThrow<AlreadyCanceledFoundException> {
                        cancelOrderService.command(registeredOrderIdCommand)
                    }
                }
            }

            context("주문이 취소 상태가 아니며") {
                val expectedOrder = Order("registeredConsumerId")
                val expectedPayment = Payment(
                    Payment.PaymentMethodType.CREDITCARD,
                    Payment.CardCompany.SAMSUNG,
                    "1234567890",
                    "username",
                    expectedOrder
                )
                expectedPayment.addExternalPaymentId("123128471982")
                expectedOrder.addPayment(expectedPayment)
                every { mockOrderPersistencePort.findOrderById("registeredOrderId") } answers { expectedOrder }

                context("해당 주문의 배송 원본 데이터의 상태가 대기중(AWAITING)이 아니면") {
                    val expectedDelivery = DeliveryResponseDto(
                        "id",
                        "registeredOrderId",
                        DeliveryResponseDto.DeliveryStatus.INPROGRESS,
                        null
                    )
                    every { mockDeliveryServiceRestPort.getDeliveryByOrderId("registeredOrderId") } answers { expectedDelivery }
                    it("주문 취소에 실패하고 UncancellableException 발생") {
                        shouldThrow<UncancellableException> {
                            cancelOrderService.command(registeredOrderIdCommand)
                        }
                    }
                }

                context("해당 주문의 배송 원본 데이터의 상태가 대기중(AWAITING)이고") {
                    val expectedDelivery = DeliveryResponseDto(
                        "id",
                        "registeredOrderId",
                        DeliveryResponseDto.DeliveryStatus.AWAITING,
                        null
                    )
                    every { mockDeliveryServiceRestPort.getDeliveryByOrderId("registeredOrderId") } answers { expectedDelivery }

                    context("외부 결제 취소에 실패하면") {
                        every { mockPaymentPort.cancelPayment(any()) } answers { false }
                        it("주문 취소에 실패하고 FailedCancelPaymentException 발생") {
                            shouldThrow<FailedCancelPaymentException> {
                                cancelOrderService.command(registeredOrderIdCommand)
                            }
                        }
                    }
                }
            }
        }

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
        private val registeredOrderIdCommand: CancelOrderUseCase.CancelOrderCommand =
            CancelOrderUseCase.CancelOrderCommand("registeredOrderId")

        private val notRegisteredOrderIdCommand: CancelOrderUseCase.CancelOrderCommand =
            CancelOrderUseCase.CancelOrderCommand("notRegisteredOrderId")
    }
}