package com.qpang.deliveryservice.adapter.`in`.event.dto

data class OrderEvent(
    val productList: List<Product>,
    val orderId: String
) {
    data class Product(
        val id: String,
        val count: Long
    )
}