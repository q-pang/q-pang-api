package com.qpang.orderservice.application.port.out.rest.dto

data class ProductResponseDto(
    val id: String,
    val name: String,
    val stock: Long,
    val price: Long,
    val category: ProductCategoryResponseDto
)
