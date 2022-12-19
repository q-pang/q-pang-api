package com.qpang.productservice.adapter.`in`.rest.dto

import com.qpang.productservice.application.port.`in`.usecase.info.ProductCategoryInfo

data class ProductCategoryResponseDto(
    val id: String,
    val name: String
) {
    companion object {
        fun from(info: ProductCategoryInfo): ProductCategoryResponseDto = ProductCategoryResponseDto(
            id = info.id,
            name = info.name
        )
    }
}
