package com.qpang.productservice.adapter.`in`.event.dto

data class OrderEvent(
    val productList: List<Product>,
) {
    data class Product(
        val id: String,
        val count: Long
    )
}