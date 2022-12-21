package com.qpang.orderservice.application.port.`in`

import com.qpang.orderservice.application.port.`in`.dto.OrderItemCommand
import com.qpang.orderservice.application.port.`in`.dto.OrderItemInfo
import com.qpang.orderservice.application.port.`in`.dto.PaymentCommand
import com.qpang.orderservice.application.port.`in`.dto.PaymentInfo
import com.qpang.orderservice.domain.Order

interface RegisterOrderUseCase {
    fun command(command: RegisterOrderCommand): RegisterOrderInfo

    data class RegisterOrderCommand(
        val consumerId: String,
        val orderItemCommands: List<OrderItemCommand>,
        val paymentCommand: PaymentCommand
    )

    data class RegisterOrderInfo(
        val orderId: String,
        val totalPrice: Long,
        val consumerId: String,
        val orderItemInfos: List<OrderItemInfo>,
        val paymentInfo: PaymentInfo
    ) {
        companion object {
            fun from(order: Order): RegisterOrderInfo = RegisterOrderInfo(
                orderId = order.getId(),
                totalPrice = order.totalPrice,
                consumerId = order.consumerId,
                orderItemInfos = order.orderItems.map { OrderItemInfo.from(it) },
                paymentInfo = PaymentInfo.from(order.payment!!)
            )
        }
    }
}