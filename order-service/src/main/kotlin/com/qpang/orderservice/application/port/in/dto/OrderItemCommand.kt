package com.qpang.orderservice.application.port.`in`.dto

data class OrderItemCommand(
    val name: String,
    val price: Long,
    val count: Long,
    val productId: String
)