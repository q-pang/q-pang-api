package com.qpang.orderservice.application.port.out.event.dto

data class CancelOrderEvent(
    val productList: List<Product>,
    val orderId: String
) {
    data class Product(
        val id: String,
        val count: Long
    )
}