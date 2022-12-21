package com.qpang.orderservice.application.service

import com.qpang.orderservice.application.port.`in`.CancelOrderUseCase
import com.qpang.orderservice.application.port.out.external.PaymentPort
import com.qpang.orderservice.application.port.out.persistence.OrderPersistencePort
import com.qpang.orderservice.application.service.exception.OrderNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CancelOrderService(
    private val orderPersistencePort: OrderPersistencePort,
    private val paymentPort: PaymentPort
) : CancelOrderUseCase {
    @Transactional
    override fun command(command: CancelOrderUseCase.CancelOrderCommand): CancelOrderUseCase.CancelOrderInfo {
        val savedOrder = orderPersistencePort.findOrderById(command.orderId)
        savedOrder?: throw OrderNotFoundException(command.orderId)
        savedOrder.cancel()

        paymentPort.cancelPayment(savedOrder.payment?.externalPaymentId!!)

        return CancelOrderUseCase.CancelOrderInfo.from(savedOrder)
    }
}