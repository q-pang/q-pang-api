package com.qpang.productservice.adapter.`in`.event.dto

data class CancelOrderEvent(
    val productList: List<Product>,
    val orderId: String
) {
    data class Product(
        val id: String,
        val count: Long
    )
}