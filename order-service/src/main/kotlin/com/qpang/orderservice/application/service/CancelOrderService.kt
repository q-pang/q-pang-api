package com.qpang.orderservice.application.service

import com.qpang.orderservice.application.port.`in`.CancelOrderUseCase
import com.qpang.orderservice.application.port.out.persistence.OrderPersistencePort
import com.qpang.orderservice.application.service.exception.OrderNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CancelOrderService(
    private val orderPersistencePort: OrderPersistencePort,
) : CancelOrderUseCase {
    @Transactional
    override fun command(command: CancelOrderUseCase.CancelOrderCommand): CancelOrderUseCase.CancelOrderInfo {
        val savedOrder = orderPersistencePort.findOrderById(command.orderId)
        savedOrder?: throw OrderNotFoundException(command.orderId)
        savedOrder.cancel()

        return CancelOrderUseCase.CancelOrderInfo.from(savedOrder)
    }
}