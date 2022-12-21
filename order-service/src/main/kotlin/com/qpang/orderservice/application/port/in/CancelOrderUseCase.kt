package com.qpang.orderservice.application.port.`in`

import com.qpang.orderservice.application.port.`in`.dto.OrderItemInfo
import com.qpang.orderservice.application.port.`in`.dto.PaymentInfo
import com.qpang.orderservice.domain.Order

interface CancelOrderUseCase {
    fun command(command: CancelOrderCommand): CancelOrderInfo

    data class CancelOrderCommand(
        val orderId: String
    )

    data class CancelOrderInfo(
        val orderId: String,
        val totalPrice: Long,
        val consumerId: String,
        val orderItemInfos: List<OrderItemInfo>,
        val paymentInfo: PaymentInfo
    ) {
        companion object {
            fun from(order: Order): CancelOrderInfo = CancelOrderInfo(
                orderId = order.getId(),
                totalPrice = order.totalPrice,
                consumerId = order.consumerId,
                orderItemInfos = order.orderItems.map { OrderItemInfo.from(it) },
                paymentInfo = PaymentInfo.from(order.payment!!)
            )
        }
    }
}