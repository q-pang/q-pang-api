package com.qpang.orderservice.adapter.`in`.rest.dto

import com.qpang.orderservice.application.port.`in`.dto.OrderItemInfo

data class OrderItemResponseDto(
    val name: String,
    val price: Long,
    val count: Long,
    val productId: String
) {
    companion object {
        fun from(info: OrderItemInfo): OrderItemResponseDto = OrderItemResponseDto(
            name = info.name,
            price = info.price,
            count = info.count,
            productId = info.productId
        )
    }
}