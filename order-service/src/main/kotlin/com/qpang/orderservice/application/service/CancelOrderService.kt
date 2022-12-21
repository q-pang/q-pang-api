package com.qpang.orderservice.application.service

import com.qpang.orderservice.application.port.`in`.CancelOrderUseCase
import com.qpang.orderservice.application.port.out.event.EventProducePort
import com.qpang.orderservice.application.port.out.event.dto.CancelOrderEvent
import com.qpang.orderservice.application.port.out.external.PaymentPort
import com.qpang.orderservice.application.port.out.persistence.OrderPersistencePort
import com.qpang.orderservice.application.service.exception.OrderNotFoundException
import com.qpang.orderservice.domain.Order
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CancelOrderService(
    private val orderPersistencePort: OrderPersistencePort,
    private val paymentPort: PaymentPort,
    private val eventProducePort: EventProducePort
) : CancelOrderUseCase {
    @Transactional
    override fun command(command: CancelOrderUseCase.CancelOrderCommand): CancelOrderUseCase.CancelOrderInfo {
        val savedOrder = orderPersistencePort.findOrderById(command.orderId)
        savedOrder ?: throw OrderNotFoundException(command.orderId)
        savedOrder.cancel()

        paymentPort.cancelPayment(savedOrder.payment?.externalPaymentId!!)

        val newCancelOrderEvent = createCancelOrderEvent(savedOrder)
        eventProducePort.cancelOrder(newCancelOrderEvent)

        return CancelOrderUseCase.CancelOrderInfo.from(savedOrder)
    }

    private fun createCancelOrderEvent(savedOrder: Order) = CancelOrderEvent(
        productList = savedOrder.orderItems.map {
            CancelOrderEvent.Product(id = it.productId, count = it.count)
        },
        orderId = savedOrder.getId()
    )
}