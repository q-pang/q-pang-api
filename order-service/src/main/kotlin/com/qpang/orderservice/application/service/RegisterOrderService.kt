package com.qpang.orderservice.application.service

import com.qpang.orderservice.application.port.`in`.RegisterOrderUseCase
import com.qpang.orderservice.application.port.out.persistence.OrderPersistencePort
import com.qpang.orderservice.domain.Order
import com.qpang.orderservice.domain.OrderItem
import com.qpang.orderservice.domain.Payment
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterOrderService(
    private val orderPersistencePort: OrderPersistencePort
) : RegisterOrderUseCase {
    @Transactional
    override fun command(command: RegisterOrderUseCase.RegisterOrderCommand): RegisterOrderUseCase.RegisterOrderInfo {
        val savedOrder = orderPersistencePort.saveOrder(Order(command.consumerId))
        val newOrderItems = createNewOrderItems(command, savedOrder)
        val newPayment = createNewPayment(command, savedOrder)
        val totalPrice = calculateTotalCount(newOrderItems)
        savedOrder.addOrderItems(newOrderItems)
        savedOrder.addPayment(newPayment)
        savedOrder.addTotalPrice(totalPrice)

        return RegisterOrderUseCase.RegisterOrderInfo.from(savedOrder)
    }

    private fun createNewOrderItems(
        command: RegisterOrderUseCase.RegisterOrderCommand,
        savedOrder: Order
    ) = command.orderItemCommands.map {
        OrderItem(
            name = it.name,
            price = it.price,
            count = it.count,
            productId = it.productId,
            savedOrder
        )
    }

    private fun createNewPayment(
        command: RegisterOrderUseCase.RegisterOrderCommand,
        savedOrder: Order
    ) = Payment(
        type = command.paymentCommand.type,
        company = command.paymentCommand.company,
        number = command.paymentCommand.number,
        username = command.paymentCommand.username,
        savedOrder
    )

    private fun calculateTotalCount(newOrderItems: List<OrderItem>): Long {
        var totalPrice = 0L
        newOrderItems.forEach {
            totalPrice += it.price * it.count
        }

        return totalPrice
    }
}