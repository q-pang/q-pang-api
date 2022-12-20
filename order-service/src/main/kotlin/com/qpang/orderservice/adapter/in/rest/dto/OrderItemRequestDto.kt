package com.qpang.orderservice.adapter.`in`.rest.dto

import com.qpang.orderservice.application.port.`in`.dto.OrderItemCommand

data class OrderItemRequestDto(
    val name: String,
    val price: Long,
    val count: Long,
    val productId: String
) {
    fun toCommand(): OrderItemCommand = OrderItemCommand(
        name = name,
        price = price,
        count = count,
        productId = productId
    )
}