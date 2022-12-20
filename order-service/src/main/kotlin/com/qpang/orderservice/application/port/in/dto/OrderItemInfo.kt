package com.qpang.orderservice.application.port.`in`.dto

import com.qpang.orderservice.domain.OrderItem

data class OrderItemInfo(
    val name: String,
    val price: Long,
    val count: Long,
    val productId: String
) {
    companion object {
        fun from(orderItem: OrderItem): OrderItemInfo = OrderItemInfo(
            name = orderItem.name,
            price = orderItem.price,
            count = orderItem.count,
            productId = orderItem.productId
        )
    }
}
